package com.projeto2.exercicio.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Departamento {
    @Column(unique = true, nullable = false)
    private String dnome;
    @Id
    private int Dnumero;

    //relacao departamento - localizacoes
    @OneToMany(mappedBy = "dept")
    private List<Localizacoes> locais;

    //relacao departamento - funcionario
    @OneToMany(mappedBy = "departamento")
    private List<Funcionario> colaboradores;

    //relacao gerencia entre departamento - funcionario
    @OneToOne
    @JoinColumn(name = "funcionario_cpf")
    private Funcionario gerente;
    private Date data_gerente;

    //relacao departamento - projeto
    @OneToMany(mappedBy = "dept_projeto")
    private List<Projeto> projetos;
}
