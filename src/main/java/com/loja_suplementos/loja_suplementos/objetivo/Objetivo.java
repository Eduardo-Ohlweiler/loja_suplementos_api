package com.loja_suplementos.loja_suplementos.objetivo;

import jakarta.persistence.*;

@Entity
@Table(name = "objetivo")
public class Objetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(length=100, nullable=false)
    private String objetivoNome;

    public Integer getId() {
        return id;
    }

    public String getObjetivo_nome() {
        return objetivoNome;
    }

    public void setObjetivo_nome(String objetivo_nome) {
        this.objetivoNome = objetivo_nome;
    }
}
