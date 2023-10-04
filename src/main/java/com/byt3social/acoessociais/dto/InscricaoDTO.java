package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InscricaoDTO(
        @JsonProperty("usuario_id")
        Integer usuarioId,
        @JsonProperty("acao_id")
        Integer acaoId
) {
}
