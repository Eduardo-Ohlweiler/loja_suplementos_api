package com.loja_suplementos.loja_suplementos.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByCategoriaId(Integer categoriaId);
    List<Produto> findByObjetivoId(Integer objetivoId);
}
