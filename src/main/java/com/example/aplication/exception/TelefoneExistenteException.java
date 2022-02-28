package com.example.aplication.exception;

public class TelefoneExistenteException extends RuntimeException {

    public TelefoneExistenteException(String ddd, String numero) {
        super(String.format("Já existe pessoa cadastrada com o telefone (%s)%s", ddd, numero));
    }

}
