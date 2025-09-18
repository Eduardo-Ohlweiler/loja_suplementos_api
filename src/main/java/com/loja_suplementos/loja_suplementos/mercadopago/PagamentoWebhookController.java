package com.loja_suplementos.loja_suplementos.mercadopago;

import com.loja_suplementos.loja_suplementos.pedido.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercadopago")
public class PagamentoWebhookController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/webhook")
    @Transactional
    private ResponseEntity<HttpStatus> processarPagamento(@RequestBody Object body){
        pedidoService.processarPagamento(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
