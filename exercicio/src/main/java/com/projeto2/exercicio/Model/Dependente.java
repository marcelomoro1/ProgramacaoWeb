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
    @ManyToOne //Relaciona com a classe Funcionario
    @MapsId("funcionario_cpf") //preenche a funcionario_cpf da dependenteId
    @JoinColumn(name = "fcpf", nullable = false) //nome da chave estrangeira
    private Funcionario funcionario;
}
