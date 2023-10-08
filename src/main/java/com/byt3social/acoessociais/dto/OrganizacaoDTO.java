package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrganizacaoDTO(
        @JsonProperty("nome_empresarial")
        String nomeEmpresarial,
        String cnpj
) {
}
