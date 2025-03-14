package com.projeto2.exercicio.Model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.io.Serializable;

//chave primaria composta
@Embeddable
public class DependenteId implements Serializable {
    private String funcionario_cpf;
    private String nome_dependente;

}
