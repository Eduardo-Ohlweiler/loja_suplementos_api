package com.loja_suplementos.loja_suplementos.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByCategoriaNome(String categoriaNome);

    Optional<Categoria> findByCategoriaNomeIgnoreCase(String categoriaNome);

    boolean existsByCategoriaNome(String categoriaNome);
}
