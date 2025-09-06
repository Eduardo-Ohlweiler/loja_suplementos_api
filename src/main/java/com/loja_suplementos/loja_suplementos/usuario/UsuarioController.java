package com.loja_suplementos.loja_suplementos.usuario;

import com.loja_suplementos.loja_suplementos.exceptions.UnauthorizedException;
import com.loja_suplementos.loja_suplementos.objetivo.Objetivo;
import com.loja_suplementos.loja_suplementos.usuario.dtos.UsuarioCreateDto;
import com.loja_suplementos.loja_suplementos.usuario.dtos.UsuarioUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuário", description = "Operações relacionadas aos usuários do sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Operation(summary = "Lista todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> usuarios = this.service.getAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Cria um novo usuário, rota disponivel apenas para usuarios administrativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrado")
    })
    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioCreateDto dto, Authentication auth) {
        Integer usuarioId     = (Integer) auth.getPrincipal();
        Usuario usuarioLogado = service.findById(usuarioId);
        if(usuarioLogado.getRole() != Role.ADMIN)
            throw new UnauthorizedException("Acesso negado: apenas administradores podem criar usuários");

        Usuario usuario = this.service.create(dto, usuarioId);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @Operation(summary  = "Delete de usuarios por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id, Authentication auth){
        Integer usuarioId     = (Integer) auth.getPrincipal();
        Usuario usuarioLogado = service.findById(usuarioId);
        if(usuarioLogado.getRole() != Role.ADMIN)
            throw new UnauthorizedException("Acesso negado: apenas administradores podem deletar usuários");

        this.service.delete(id, usuarioLogado);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @Valid @RequestBody UsuarioUpdateDto dto, Authentication auth){
        Integer usuarioId     = (Integer) auth.getPrincipal();

        Usuario usuario = this.service.update(id, dto, usuarioId);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Busca usuario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        Usuario usuario = service.findById(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
