package com.loja_suplementos.loja_suplementos.usuario.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loja_suplementos.loja_suplementos.usuario.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto {

    @NotBlank(message="O nome é obrigatório")
    @Size(min=3, max=100, message="O nome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Nome completo do usuário", example = "Eduardo Rodrigo")
    private String nome;

    @NotBlank(message="O email é obrigatório")
    @Email(message="O email deve ser valido")
    @Schema(description = "Email do usuário", example = "eduardo@email.com")
    private String email;

    @Size(min=3, max=200, message="O endereço deve ter entre 3 e 200 caracteres")
    @Schema(description = "Endereço completo do usuário", example = "Rua Carlos Vagner, numero 123, bairro Centro, Venâncio Aires/RS ")
    private String endereco;

    @NotBlank(message="A senha é obrigatória")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula e um número"
    )
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "O número de telefone é obrigatório")
    @Pattern(
            regexp = "\\d{11}",
            message = "O número de telefone deve ter 11 dígitos (DD + número)"
    )
    @JsonProperty("telefone")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Permissão do usuario", example = "ADMIN | USER")
    private Role role = Role.USER;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
