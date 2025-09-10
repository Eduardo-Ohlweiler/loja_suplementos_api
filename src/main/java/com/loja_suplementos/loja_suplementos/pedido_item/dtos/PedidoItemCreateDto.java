package com.loja_suplementos.loja_suplementos.pedido_item.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PedidoItemCreateDto {

    @NotNull(message = "O produto é obrigatório")
    @Schema(description = "ID do produto adicionado ao pedido", example = "10")
    private Integer produtoId;

    @NotNull(message = "A quantidade é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "Quantidade deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    @Schema(description = "Quantidade do produto", example = "2.00")
    private BigDecimal quantidade;

    @DecimalMin(value = "0.00", message = "O desconto não pode ser negativo")
    @Digits(integer = 10, fraction = 2, message = "Desconto deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    @Schema(description = "Valor de desconto aplicado ao item (opcional)", example = "5.00")
    private BigDecimal valorDesconto;

    @DecimalMin(value = "0.00", message = "O acréscimo não pode ser negativo")
    @Digits(integer = 10, fraction = 2, message = "Acréscimo deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    @Schema(description = "Valor de acréscimo aplicado ao item (opcional)", example = "10.00")
    private BigDecimal valorAcrescimo;

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorAcrescimo() {
        return valorAcrescimo;
    }

    public void setValorAcrescimo(BigDecimal valorAcrescimo) {
        this.valorAcrescimo = valorAcrescimo;
    }
}
