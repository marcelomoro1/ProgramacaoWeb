package com.projeto2.exercicio.Model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DependenteId implements Serializable {

    private String funcionario_cpf;
    private String nome_dependente;

}
