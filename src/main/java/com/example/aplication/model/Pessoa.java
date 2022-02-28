package com.example.aplication.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String nome;

    @Column(length = 11, nullable = false)
    private String cpf;

    @OneToMany(mappedBy = "pessoa")
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "pessoa")
    private List<Telefone> telefones;


}
