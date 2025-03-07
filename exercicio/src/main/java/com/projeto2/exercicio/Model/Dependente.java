package com.projeto2.exercicio.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Dependente {
    private int nome;
    private String sexo;
    private Date dataNascimento;
    private String Parentesco;

    @EmbeddedId
    private DependenteId id;

    //Relação Funcionario_dependente
    @ManyToOne
    private Funcionario funcionario;
}
