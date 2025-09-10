package com.loja_suplementos.loja_suplementos.categoria;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(length=100, nullable=false)
    private String categoriaNome;

    public Integer getId() {
        return id;
    }


    public String getCategoria_nome() {
        return categoriaNome;
    }

    public void setCategoria_nome(String categoria_nome) {
        this.categoriaNome = categoria_nome;
    }
}
