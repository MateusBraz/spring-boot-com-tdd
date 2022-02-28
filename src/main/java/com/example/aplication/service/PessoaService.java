package com.example.aplication.service;

import com.example.aplication.model.Pessoa;
import com.example.aplication.model.Telefone;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa);

    Pessoa buscarPorTelefone(Telefone telefone);

}
