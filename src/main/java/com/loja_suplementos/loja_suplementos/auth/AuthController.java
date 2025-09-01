package com.loja_suplementos.loja_suplementos.auth;

import com.loja_suplementos.loja_suplementos.auth.dtos.LoginDto;
import com.loja_suplementos.loja_suplementos.exceptions.dtos.ExceptionResponseDto;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import com.loja_suplementos.loja_suplementos.usuario.dtos.UsuarioCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Operações relacionadas a autenticação, como cadastros de usuários comuns e login")
public class AuthController {

    @Autowired
    private AuthService service;

    @Operation(
            summary = "Cria um novo usuário comum",
            description = "Cria um novo usuário comum no sistema com base nos dados fornecidos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(schema = @Schema(implementation = Usuario.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário já existe",
                            content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioCreateDto dto) {

        Usuario usuario = this.service.create(dto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Faz login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto dto){
        String temp = this.service.login(dto);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }
}
