package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CampanhaDTO(
        Integer id,
        Integer meta,
        @JsonProperty("meta_descricao")
        String  metaDescricao
) {
}
