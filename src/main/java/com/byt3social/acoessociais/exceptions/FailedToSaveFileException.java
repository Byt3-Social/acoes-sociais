package com.byt3social.acoessociais.exceptions;

public class FailedToSaveFileException extends RuntimeException {
    public FailedToSaveFileException() {
        super("Falha ao salvar arquivo");
    }
}
