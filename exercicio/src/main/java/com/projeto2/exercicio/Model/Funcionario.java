package com.projeto2.exercicio.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Funcionario {

    @Embedded
    @Column(nullable = false)
    private Nome nome;

    @Column(columnDefinition = "CHAR(11)",nullable = false, unique = true)
    @Id
    private String cpf;

    @Column(nullable = false)
    private Date dataNascimento;

    @Column(nullable = false)
    private float salario;

    @Column(columnDefinition = "CHAR(1)", nullable = false)
    private String sexo;

    @Column(columnDefinition = "CHAR(11)")
    private String supervisor_cpf;

    @Column(nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)//1 supervisor pode ter muitos supervisionados
    private List<Funcionario> supervisionados;

    @ManyToOne//Muitos funcionarios podem ter o mesmo supervisor
    @JoinColumn(name = "supervisor_cpf")
    private Funcionario supervisor;

    //Dependente
    @OneToMany(mappedBy = "funcionario")
    private List<Dependente> dependentes;
    

}
