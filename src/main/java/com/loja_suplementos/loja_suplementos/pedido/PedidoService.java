package com.loja_suplementos.loja_suplementos.pedido;

import com.loja_suplementos.loja_suplementos.exceptions.NotFoundException;
import com.loja_suplementos.loja_suplementos.pedido.dtos.PedidoCreateDto;
import com.loja_suplementos.loja_suplementos.pedido_item.PedidoItem;
import com.loja_suplementos.loja_suplementos.pedido_item.PedidoItemService;
import com.loja_suplementos.loja_suplementos.pedido_item.dtos.PedidoItemCreateDto;
import com.loja_suplementos.loja_suplementos.produto.Produto;
import com.loja_suplementos.loja_suplementos.produto.ProdutoRepository;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import com.loja_suplementos.loja_suplementos.utils.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public Pedido create(PedidoCreateDto dto){
        Usuario usuario   = this.validadorUsuario.getUsuarioLogado();

        Pedido pedido     = new Pedido();
        pedido.setUsuario(usuario);

        // Lista de pedidos
        List<PedidoItem> itensPedido = new ArrayList<>();
        BigDecimal totalValor      = BigDecimal.ZERO;
        BigDecimal totalQuantidade = BigDecimal.ZERO;

        for (PedidoItemCreateDto itemDto : dto.getItens()){

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
        }

        pedido.setPedidoItems(itensPedido);
        pedido.setValor_total(totalValor);
        pedido.setQuantidade_total(totalQuantidade);

        return pedidoRepository.save(pedido);
    }

    public Pedido cancelarPedido(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        pedido.setpStatus(PStatus.CANCELADO);
        return pedidoRepository.save(pedido);
    }
}
