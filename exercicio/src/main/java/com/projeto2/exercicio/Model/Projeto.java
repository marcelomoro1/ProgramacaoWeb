package com.projeto2.exercicio.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Projeto {
    @Column(unique = true, nullable = false)
    private String projnome;
    @Column(unique = true, nullable = false)
    private String projlocal;

    @Id
    private int Pnumero;

    //relacionamento projeto - departamento
    @ManyToOne
    @JoinColumn(name = "Dnumero")
    private Departamento dept_projeto;


    //relacao projeto - trabalhaem
    @OneToMany(mappedBy = "trabalhaem_projeto")
    private List<TrabalhaEm> projeto_trabalhaem;
}
