package com.loja_suplementos.loja_suplementos.categoria;

import com.loja_suplementos.loja_suplementos.categoria.dtos.CategoriaDto;
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
@RequestMapping("/api/categoria")
@Tag(name = "Categoria", description = "Operações relacionadas as categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Operation(summary = "Cria uma nova categoria, rota disponivel apenas para usuarios administrativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Categoria já cadastrada")
    })
    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody CategoriaDto dto) {

        Categoria categoria = this.service.create(dto);
        return new ResponseEntity<>(categoria, HttpStatus.CREATED);
    }

    @Operation(summary  = "Delete de categoria por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id){

        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Atualiza parcialmente uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Categoria já cadastrada")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDto dto) {
        Categoria categoria = service.update(id, dto);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @Operation(summary = "Busca uma categoria por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
        Categoria categoria = service.findById(id);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @Operation(summary = "Lista todas as categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<Categoria>> getAll(){
        List<Categoria> categorias = this.service.getAll();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
}
