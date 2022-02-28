package com.example.aplication.repository;

import com.example.aplication.model.Pessoa;
import com.example.aplication.repository.filtro.PessoaFiltro;

import java.util.List;

public interface PessoaRepositoryQueries {

    List<Pessoa> filtrar(PessoaFiltro filtro);

}
