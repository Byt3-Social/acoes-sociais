package com.byt3social.acoessociais.dto;

import org.springframework.validation.FieldError;

public record FieldErrorDTO(String field, String message) {
    public FieldErrorDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
