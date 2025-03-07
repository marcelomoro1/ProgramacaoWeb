package com.projeto2.exercicio.Model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DependenteId implements Serializable {

    private String fcpf;
    private String nome_dependente;

}
