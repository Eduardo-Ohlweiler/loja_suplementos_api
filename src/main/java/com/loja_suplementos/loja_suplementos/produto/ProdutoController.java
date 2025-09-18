package com.loja_suplementos.loja_suplementos.produto;

import com.loja_suplementos.loja_suplementos.produto.dtos.CreateProdutoDto;
import com.loja_suplementos.loja_suplementos.produto.dtos.UpdateProdutoDto;
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

    @Operation(summary = "Atualiza um produto existente, rota disponível apenas para usuários administrativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Integer id, @Valid @RequestBody UpdateProdutoDto dto) {

        Produto produto = this.produtoService.update(id, dto);
        return ResponseEntity.ok(produto);
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

    @Operation(summary = "Busca produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Integer id) {
        Produto produto = produtoService.findById(id);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    @Operation(summary  = "Delete de produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id){

        this.produtoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
