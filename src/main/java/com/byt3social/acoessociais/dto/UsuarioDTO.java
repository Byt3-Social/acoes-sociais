package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioDTO(
        Integer id,
        @JsonProperty("name")
        String nome,
        String email
) {
}
