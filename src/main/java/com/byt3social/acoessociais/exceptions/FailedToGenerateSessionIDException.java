package com.byt3social.acoessociais.exceptions;

public class FailedToGenerateSessionIDException extends RuntimeException {
    public FailedToGenerateSessionIDException() {
        super("Falha ao inicializar sessão para o doador");
    }
}
