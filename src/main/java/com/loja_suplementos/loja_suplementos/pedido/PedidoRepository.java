package com.loja_suplementos.loja_suplementos.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findByPagamentoId(String pagamentoId);
}
