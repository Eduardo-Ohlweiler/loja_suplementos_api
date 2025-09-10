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
        double totalValor            = 0.0;
        double totalQuantidade       = 0.0;

        for (PedidoItemCreateDto itemDto : dto.getItens()){

            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + itemDto.getProdutoId()));

            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setProduto(produto);

            //QUANTIDADE
            double quantidade = 0.0;
            if (itemDto.getQuantidade() != null) {
                quantidade = itemDto.getQuantidade().doubleValue();
            }
            item.setQuantidade(new BigDecimal(quantidade));

            //VALOR UNITARIO
            double valorUnitario = 0.0;
            if (produto.getValor() != null) {
                valorUnitario = produto.getValor().doubleValue();
            }
            item.setValorUnitario(new BigDecimal(valorUnitario));

            //SUBTOTAL
            double subTotal = 0.0;
            if (quantidade > 0 && valorUnitario > 0) {
                subTotal = quantidade * valorUnitario;
            }
            item.setSubTotal(new BigDecimal(subTotal));

            //DESCONTO
            double desconto = 0.0;
            if (itemDto.getValorDesconto() != null) {
                desconto = itemDto.getValorDesconto().doubleValue();
            }
            item.setValorDesconto(new BigDecimal(desconto));

            //ACRESCIMO
            double acrescimo = 0.0;
            if (itemDto.getValorAcrescimo() != null) {
                acrescimo = itemDto.getValorAcrescimo().doubleValue();
            }
            item.setValorAcrescimo(new BigDecimal(acrescimo));

            //VALOR TOTAL DO ITEM
            double totalItem = 0.0;
            if (subTotal > 0) {
                totalItem = subTotal - desconto + acrescimo;
            }
            item.setValorTotal(new BigDecimal(totalItem));

            itensPedido.add(item);
            totalValor      += totalItem;
            totalQuantidade += quantidade;
        }

        pedido.setPedidoItems(itensPedido);
        pedido.setValor_total(new BigDecimal(totalValor));
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
