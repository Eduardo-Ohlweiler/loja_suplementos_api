package com.loja_suplementos.loja_suplementos.produto;

import com.loja_suplementos.loja_suplementos.objetivo.Objetivo;
import com.loja_suplementos.loja_suplementos.objetivo.dtos.ObjetivoDto;
import com.loja_suplementos.loja_suplementos.produto.dtos.CreateProdutoDto;
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
@RequestMapping("/api/produto")
@Tag(name = "Produto", description = "Operações relacionadas a produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Cria um novo produto, rota disponivel apenas para usuarios administrativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Produto ja cadastrado")
    })
    @PostMapping
    public ResponseEntity<Produto> create(@Valid @RequestBody CreateProdutoDto dto) {

        Produto produto = this.produtoService.create(dto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        List<Produto> produtos = this.produtoService.getAll();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
}
