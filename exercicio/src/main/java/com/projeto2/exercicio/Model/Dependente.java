package com.projeto2.exercicio.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Dependente {
    @Id
    private int nome;
    private String sexo;
    private Date dataNascimento;
    private String Parentesco;
}
