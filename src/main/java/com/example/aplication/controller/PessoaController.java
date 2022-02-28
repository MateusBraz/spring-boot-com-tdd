package com.example.aplication.controller;

import com.example.aplication.exception.CpfExistenteException;
import com.example.aplication.exception.TelefoneExistenteException;
import com.example.aplication.exception.TelefoneNaoEncontradoException;
import com.example.aplication.model.Pessoa;
import com.example.aplication.model.Telefone;
import com.example.aplication.repository.PessoaRepository;
import com.example.aplication.repository.filtro.PessoaFiltro;
import com.example.aplication.service.PessoaService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping("/{ddd}/{numero}")
    public ResponseEntity<Pessoa> buscarPorDddENumeroTelefone(@PathVariable String ddd, @PathVariable String numero) {
        final Telefone telefone = new Telefone();
        telefone.setDdd(ddd);
        telefone.setNumero(numero);

        final Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);
        return ResponseEntity.ok(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> adicionar(@RequestBody Pessoa pessoa, HttpServletResponse response) {
        final Pessoa pessoaSalva = pessoaService.salvar(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{ddd}/{numero}")
                .buildAndExpand(pessoa.getTelefones().get(0).getDdd(), pessoa.getTelefones().get(0).getNumero()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        return new ResponseEntity<>(pessoaSalva, HttpStatus.CREATED);
    }

    @PostMapping("/filtrar")
    public ResponseEntity<List<Pessoa>> filtrar(@RequestBody PessoaFiltro pessoaFiltro) {
        final List<Pessoa> pessoas = pessoaRepository.filtrar(pessoaFiltro);
        return ResponseEntity.ok(pessoas);
    }

    @ExceptionHandler(TelefoneNaoEncontradoException.class)
    public ResponseEntity<Erro> handleTelefoneNaoEncontradoException(TelefoneNaoEncontradoException exception) {
        return new ResponseEntity<>(new Erro(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CpfExistenteException.class)
    public ResponseEntity<Erro> handleCpfExistenteException(CpfExistenteException exception) {
        return new ResponseEntity<>(new Erro(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TelefoneExistenteException.class)
    public ResponseEntity<Erro> handleTelefoneExistenteException(TelefoneExistenteException exception) {
        return new ResponseEntity<>(new Erro(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    class Erro {

        @Getter
        private final String erro;

        public Erro(String erro) {
            this.erro = erro;
        }

    }

}
