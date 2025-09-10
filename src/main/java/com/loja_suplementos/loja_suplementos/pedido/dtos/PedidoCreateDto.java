package com.loja_suplementos.loja_suplementos.pedido.dtos;

import com.loja_suplementos.loja_suplementos.pedido_item.dtos.PedidoItemCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class PedidoCreateDto {

    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    @Valid
    @Schema(description = "Lista de itens do pedido", required = true)
    private List<PedidoItemCreateDto> itens;

    public List<PedidoItemCreateDto> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItemCreateDto> itens) {
        this.itens = itens;
    }
}
