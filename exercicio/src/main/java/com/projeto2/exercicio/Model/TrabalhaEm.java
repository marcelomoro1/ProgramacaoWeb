package com.projeto2.exercicio.Model;

import jakarta.persistence.*;

@Entity
public class TrabalhaEm {
    @EmbeddedId
    private TrabalhaEmId id;

    //relacao trabalhaem - projeto
    @ManyToOne
    @MapsId("Pnr")
    @JoinColumn(name = "Pnr")
    private Projeto trabalhaem_projeto;

    //relacao trabalhaem - funcionario
    @ManyToOne
    @MapsId("Fcpf")
    @JoinColumn(name = "Fcpf")
    private Funcionario trabalhaem_funcionario;
}
