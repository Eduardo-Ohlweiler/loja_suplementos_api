package com.loja_suplementos.loja_suplementos.pedido;

import com.loja_suplementos.loja_suplementos.pedido.dtos.PedidoCreateDto;
import com.loja_suplementos.loja_suplementos.produto.Produto;
import com.loja_suplementos.loja_suplementos.produto.dtos.CreateProdutoDto;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/pedido")
@Tag(name = "Pedido", description = "Operações relacionadas a pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Cria um novo pedido, retorna o id do pagamento do mercado pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    })
    @PostMapping
    public ResponseEntity<String> createPagamento(@Valid @RequestBody PedidoCreateDto dto) {

        String pagamento_id = this.pedidoService.createPedido(dto);
        return new ResponseEntity<>(pagamento_id, HttpStatus.CREATED);
    }
}
