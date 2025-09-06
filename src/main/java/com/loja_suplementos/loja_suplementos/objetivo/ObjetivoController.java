package com.loja_suplementos.loja_suplementos.objetivo;

import com.loja_suplementos.loja_suplementos.objetivo.dtos.ObjetivoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Atualiza parcialmente um objetivo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objetivo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Objetivo não encontrado"),
            @ApiResponse(responseCode = "409", description = "Objetivo já cadastrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Objetivo> update(@PathVariable Integer id, @Valid @RequestBody ObjetivoDto dto) {
        Objetivo objetivo = service.update(id, dto);
        return new ResponseEntity<>(objetivo, HttpStatus.OK);
    }

    @Operation(summary = "Busca objetivo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objetivo encontrado"),
            @ApiResponse(responseCode = "404", description = "Objetivo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Objetivo> findById(@PathVariable Integer id) {
        Objetivo objetivo = service.findById(id);
        return new ResponseEntity<>(objetivo, HttpStatus.OK);
    }

    @Operation(summary = "Lista todos os objetivos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<Objetivo>> getAll(){
        List<Objetivo> objetivos = this.service.getAll();
        return new ResponseEntity<>(objetivos, HttpStatus.OK);
    }
}
