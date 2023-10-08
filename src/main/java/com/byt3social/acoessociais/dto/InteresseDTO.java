package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InteresseDTO(
        @JsonProperty("usuario_id")
        Integer usuarioId,
        List<Integer> interesses
) {
}
