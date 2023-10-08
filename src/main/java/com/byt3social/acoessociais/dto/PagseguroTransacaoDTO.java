package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PagseguroTransacaoDTO(
        String id,
        String reference,
        Integer status,
        @JsonProperty("reference_id")
        String referenceId,
        @JsonProperty("qr_codes")
        List<QrCodeDTO> qrCodes,
        List<ChargeDTO> charges
) {
}
