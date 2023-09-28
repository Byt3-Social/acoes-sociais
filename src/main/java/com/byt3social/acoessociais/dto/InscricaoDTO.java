package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InscricaoDTO(
        Integer id,
        @JsonProperty("assinatura_digital")
        String assinaturaDigital,
        UsuarioDTO participante,
        @JsonProperty("acao_voluntariado")
        AcaoVoluntariadoDTO acaoVoluntariado
) {
}
