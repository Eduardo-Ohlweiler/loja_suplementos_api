package com.loja_suplementos.loja_suplementos.objetivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjetivoRepository extends JpaRepository<Objetivo, Integer> {

    Optional<Objetivo> findByObjetivoNome(String objetivoNome);

    Optional<Objetivo> findByObjetivoNomeIgnoreCase(String objetivoNome);

    boolean existsByObjetivoNome(String objetivoNome);
}
