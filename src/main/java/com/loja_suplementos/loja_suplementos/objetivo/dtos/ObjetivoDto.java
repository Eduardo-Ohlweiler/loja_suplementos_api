package com.loja_suplementos.loja_suplementos.objetivo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ObjetivoDto {

    @NotBlank(message="O nome do objetivo é obrigatório")
    @Size(min=3, max=100, message="O nome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Nome completo do objetivo", example = "Ganhar massa")
    private String objetivoNome;

    public String getObjetivoNome() {
        return objetivoNome;
    }

    public void setObjetivoNome(String objetivoNome) {
        this.objetivoNome = objetivoNome;
    }
}
