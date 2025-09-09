package com.loja_suplementos.loja_suplementos.produto.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class UpdateProdutoDto {
    @Size(min = 3, max = 150, message = "O nome do produto deve ter entre 3 e 150 caracteres")
    @JsonProperty("produto_nome")
    @Schema(description = "Nome completo do produto", example = "Creatina Monohidratada 300g")
    private String produtoNome;

    @Positive(message = "O valor deve ser maior que zero")
    @Schema(description = "Valor do produto", example = "120.0")
    private Double valor;

    @JsonProperty("categoria_id")
    @Schema(description = "ID da categoria associada ao produto", example = "1")
    private Integer categoriaId;

    @JsonProperty("objetivo_id")
    @Schema(description = "ID do objetivo associado ao produto", example = "2")
    private Integer objetivoId;

    @Size(max = 1000, message = "A descrição pode ter no máximo 1000 caracteres")
    @Schema(description = "Descrição detalhada do produto", example = "Creatina monohidratada de alta pureza...")
    private String descricao;

    @Schema(description = "URL da imagem do produto", example = "https://meusite.com/images/suplemento-1.png")
    @Pattern(
            regexp = "^(https?|ftp)://.*$",
            message = "A imagem deve ser uma URL válida"
    )
    private String foto;

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
