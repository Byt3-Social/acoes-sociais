package com.byt3social.acoessociais.exceptions;

public class InvalidOperationTypeException extends RuntimeException {
    public InvalidOperationTypeException() {
        super("Falha ao processar seu pedido com o arquivo informado");
    }
}
