package com.aula203.atividade1;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Data //lombok, tem getter,setter,hash,equals,tostring,etc
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nome;
    @NonNull
    private String email;


    @NonNull
    @OneToMany(mappedBy = "usuario")
    private List<Phone> telefones;



}
