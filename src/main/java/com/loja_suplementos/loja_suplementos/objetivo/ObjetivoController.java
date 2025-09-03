package com.loja_suplementos.loja_suplementos.objetivo;

import com.loja_suplementos.loja_suplementos.exceptions.UnauthorizedException;
import com.loja_suplementos.loja_suplementos.objetivo.dtos.ObjetivoDto;
import com.loja_suplementos.loja_suplementos.usuario.Role;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import com.loja_suplementos.loja_suplementos.usuario.dtos.UsuarioCreateDto;
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

@RestController
@RequestMapping("/api/objetivo")
@Tag(name = "Objetivo", description = "Operações relacionadas aos objetivos")
public class ObjetivoController {
    @Autowired
    private ObjetivoService service;

    @Operation(summary = "Cria um novo objetivo, rota disponivel apenas para usuarios administrativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Objetivo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Objetivo ja cadastrado")
    })
    @PostMapping
    public ResponseEntity<Objetivo> create(@Valid @RequestBody ObjetivoDto dto) {

        Objetivo objetivo = this.service.create(dto);
        return new ResponseEntity<>(objetivo, HttpStatus.CREATED);
    }

    @Operation(summary  = "Delete de objetivo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objetivo deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id){

        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
