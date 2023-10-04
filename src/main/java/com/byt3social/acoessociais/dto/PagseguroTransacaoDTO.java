package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PagseguroTransacaoDTO(
        String code,
        String reference,
        Integer status,
        String paymentLink
) {
}
