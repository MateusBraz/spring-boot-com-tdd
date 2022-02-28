package com.example.aplication.exception;

public class TelefoneNaoEncontradoException extends RuntimeException {

    public TelefoneNaoEncontradoException(String ddd, String telefone) {
        super(String.format("Não existe pessoa com o telefone (%s)%s", ddd, telefone));
    }

}
