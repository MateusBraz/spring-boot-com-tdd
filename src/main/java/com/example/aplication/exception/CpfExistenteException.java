package com.example.aplication.exception;

public class CpfExistenteException extends RuntimeException {

    public CpfExistenteException(String cpf) {
        super(String.format("Já existe pessoa cadastrada com o CPF '%s'", cpf));
    }
}
