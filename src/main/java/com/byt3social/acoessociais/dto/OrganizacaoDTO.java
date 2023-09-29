package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.StatusCadastro;
import com.fasterxml.jackson.annotation.JsonProperty;

public record OrganizacaoDTO(
        Integer id,
        String cnpj,
        String nome,
        @JsonProperty("status_cadastro")
        StatusCadastro statusCadastro
) {
}
