package com.projeto2.exercicio.Model;

import jakarta.persistence.*;

@Entity
public class Localizacoes {

    @EmbeddedId
    private LocalizacoesId id;

    //relacao departamento - localizacoes
    @ManyToOne
    @MapsId("Dnumero")
    @JoinColumn(name = "Dnr")
    private Departamento dept;
}
