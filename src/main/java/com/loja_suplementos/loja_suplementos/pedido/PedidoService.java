package com.loja_suplementos.loja_suplementos.pedido;

import com.loja_suplementos.loja_suplementos.exceptions.NotFoundException;
import com.loja_suplementos.loja_suplementos.mercadopago.MercadoPagoService;
import com.loja_suplementos.loja_suplementos.pedido.dtos.PedidoCreateDto;
import com.loja_suplementos.loja_suplementos.pedido_item.PedidoItem;
import com.loja_suplementos.loja_suplementos.pedido_item.PedidoItemService;
import com.loja_suplementos.loja_suplementos.pedido_item.dtos.PedidoItemCreateDto;
import com.loja_suplementos.loja_suplementos.produto.Produto;
import com.loja_suplementos.loja_suplementos.produto.ProdutoRepository;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import com.loja_suplementos.loja_suplementos.utils.ValidadorUsuario;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ValidadorUsuario validadorUsuario;

    @Autowired
    private PedidoItemService pedidoItemService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Transactional
    public String createPedido(PedidoCreateDto dto){
        Usuario usuario   = this.validadorUsuario.getUsuarioLogado();

        Pedido pedido     = new Pedido();
        pedido.setUsuario(usuario);

        // Lista de itens
        List<PedidoItem> itensPedido = new ArrayList<>();
        List<PreferenceItemRequest> itemsMercadoPago = new ArrayList<>();

        BigDecimal totalValor      = BigDecimal.ZERO;
        BigDecimal totalQuantidade = BigDecimal.ZERO;
        Integer contador = 0;

        for (PedidoItemCreateDto itemDto : dto.getItens()){
            contador++;
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + itemDto.getProdutoId()));

            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setProduto(produto);

            //QUANTIDADE
            BigDecimal quantidade = BigDecimal.ZERO;
            if (itemDto.getQuantidade() != null) {
                quantidade = itemDto.getQuantidade();
            }
            item.setQuantidade(quantidade);
            totalQuantidade = totalQuantidade.add(quantidade);

            //VALOR UNITARIO
            BigDecimal valorUnitario = BigDecimal.ZERO;
            if (produto.getValor() != null) {
                valorUnitario = produto.getValor();
            }
            item.setValorUnitario(valorUnitario);

            //SUBTOTAL
            BigDecimal subTotal = BigDecimal.ZERO;
            if (quantidade.compareTo(BigDecimal.ZERO) > 0 && valorUnitario.compareTo(BigDecimal.ZERO) > 0) {
                subTotal = valorUnitario.multiply(quantidade);
            }
            item.setSubTotal(subTotal);

            //DESCONTO
            BigDecimal desconto = BigDecimal.ZERO;
            if (itemDto.getValorDesconto() != null) {
                desconto = itemDto.getValorDesconto();
            }
            item.setValorDesconto(desconto);

            //ACRESCIMO
            BigDecimal acrescimo = BigDecimal.ZERO;
            if (itemDto.getValorAcrescimo() != null) {
                acrescimo = itemDto.getValorAcrescimo();
            }
            item.setValorAcrescimo(acrescimo);

            //VALOR TOTAL DO ITEM
            BigDecimal totalItem = subTotal.subtract(desconto).add(acrescimo);
            item.setValorTotal(totalItem);

            itensPedido.add(item);
            totalValor = totalValor.add(totalItem);

            BigDecimal valorFinalItem = valorUnitario.add(acrescimo).subtract(desconto);

            //MERCADO PAGO
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(item.getProduto().getId().toString())
                            .title(item.getProduto().getProdutoNome())
                            .description(item.getProduto().getDescricao())
                            .pictureUrl(item.getProduto().getFoto())
                            .categoryId(item.getProduto().getCategoria().getId().toString())
                            .quantity(quantidade.intValue())
                            .currencyId("BRL")
                            .unitPrice(valorFinalItem)
                            .build();

            itemsMercadoPago.add(itemRequest);
        }

        pedido.setPedidoItems(itensPedido);
        pedido.setValor_total(totalValor);
        pedido.setQuantidade_total(totalQuantidade);

        Pedido pedidoDB = this.pedidoRepository.save(pedido);
        Preference preference =  this.mercadoPagoService.create(itemsMercadoPago,pedidoDB.getId());

        return preference.getInitPoint();
    }

    public Pedido cancelarPedido(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        pedido.setpStatus(PStatus.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void processarPagamento(Object body){

        if(!(body instanceof Map<?,?> map))
            throw new NotFoundException("Webhook inválido");

        Object dataObjeto = map.get("data");
        if(!(dataObjeto instanceof Map<?,?> data))
            throw new NotFoundException("Data inválido ou nulo");

        Object pagamentoIdObjeto = data.get("id");
        if(pagamentoIdObjeto == null)
            throw new NotFoundException("Id do pagamento não encontrado");

        String pagamentoId = pagamentoIdObjeto.toString();
        Payment payment = this.buscarPagamento(Long.valueOf(pagamentoId));

        Pedido pedido = pedidoRepository.findById(Integer.valueOf(payment.getExternalReference()))
                .orElseThrow(() -> new NotFoundException(
                        "Pedido não encontrado"
                ));

        switch (payment.getStatus()){
            case "approved":
                pedido.setpStatus(PStatus.PAGO);
                pedido.setPagamento_id(payment.getId().toString());
                break;
            case "pending":
                pedido.setpStatus(PStatus.AGUARDANDO);
                break;
            case "rejected":
                pedido.setpStatus(PStatus.CANCELADO);
                pedido.setPagamento_id(payment.getId().toString());
                break;
            default:
                pedido.setpStatus(PStatus.AGUARDANDO);
        }
        pedidoRepository.save(pedido);
    }

    public Payment buscarPagamento(Long pagamentoId){
        try {
            PaymentClient paymentClient = new PaymentClient();
            Payment payment = paymentClient.get(pagamentoId);
            return payment;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pagamento: " + pagamentoId, e);
        }
    }
}
