package com.loja_suplementos.loja_suplementos.produto.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateProdutoDto {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 3, max = 150, message = "O nome do produto deve ter entre 3 e 150 caracteres")
    @JsonProperty("produto_nome")
    @Schema(description = "Nome completo do produto", example = "Creatina Monohidratada 300g")
    private String produtoNome;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    @Schema(description = "Valor do produto", example = "120.0")
    private Double valor;

    @NotNull(message = "A categoria é obrigatória")
    @JsonProperty("categoria_id")
    @Schema(description = "ID da categoria associada ao produto", example = "1")
    private Integer categoriaId;

    @NotNull(message = "O objetivo é obrigatório")
    @JsonProperty("objetivo_id")
    @Schema(description = "ID do objetivo associado ao produto", example = "2")
    private Integer objetivoId;

    @Size(max = 1000, message = "A descrição pode ter no máximo 1000 caracteres")
    @Schema(description = "Descrição detalhada do produto", example = "Creatina monohidratada de alta pureza...")
    private String descricao;

    @NotBlank(message = "A foto é obrigatória")
    @Schema(description = "Caminho ou URL da imagem do produto", example = "/images/suplemento-1.png")
    private String foto;

    // Getters e Setters
    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getObjetivoId() {
        return objetivoId;
    }

    public void setObjetivoId(Integer objetivoId) {
        this.objetivoId = objetivoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
