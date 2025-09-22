package com.loja_suplementos.loja_suplementos.auth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

public class LoginResponseDto {

    @Schema(description = "Token de acesso")
    private String accessToken;

    @JsonProperty("user")
    private Usuario user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Usuario getUsuario() {
        return user;
    }

    public void setUsuario(Usuario usuario) {
        this.user = usuario;
    }
}
