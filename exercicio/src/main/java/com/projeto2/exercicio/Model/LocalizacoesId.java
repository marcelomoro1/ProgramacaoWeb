package com.projeto2.exercicio.Model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class LocalizacoesId implements Serializable {
    
    private String dlocal;
    private String Dnumero;
}
