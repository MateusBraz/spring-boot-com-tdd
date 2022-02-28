package com.example.aplication.service.impl;

import com.example.aplication.exception.CpfExistenteException;
import com.example.aplication.exception.TelefoneExistenteException;
import com.example.aplication.exception.TelefoneNaoEncontradoException;
import com.example.aplication.model.Pessoa;
import com.example.aplication.model.Telefone;
import com.example.aplication.repository.PessoaRepository;
import com.example.aplication.service.PessoaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());

        if (optional.isPresent()) {
            throw new CpfExistenteException(pessoa.getCpf());
        }

        String ddd = pessoa.getTelefones().get(0).getDdd();
        String numero = pessoa.getTelefones().get(0).getNumero();
        optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(ddd, numero);

        if (optional.isPresent()) {
            throw new TelefoneExistenteException(ddd, numero);
        }

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorTelefone(Telefone telefone) {
        final Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
        return optional.orElseThrow(() -> new TelefoneNaoEncontradoException(telefone.getDdd(), telefone.getNumero()));
    }

}
