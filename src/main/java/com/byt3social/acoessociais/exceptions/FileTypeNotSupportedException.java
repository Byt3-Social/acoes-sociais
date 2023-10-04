package com.byt3social.acoessociais.exceptions;

public class FileTypeNotSupportedException extends RuntimeException {
    public FileTypeNotSupportedException() {
        super("Tipo de arquivo não suportado");
    }
}
