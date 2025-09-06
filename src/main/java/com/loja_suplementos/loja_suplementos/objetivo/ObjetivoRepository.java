package com.loja_suplementos.loja_suplementos.objetivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjetivoRepository extends JpaRepository<Objetivo, Integer> {

    Optional<Objetivo> findByName(String name);

    Optional<Objetivo> findByNameIgnoreCase(String name);

    boolean existsByName(String name);
}
