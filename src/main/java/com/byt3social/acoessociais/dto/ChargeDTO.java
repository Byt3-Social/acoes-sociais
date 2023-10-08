package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.StatusDoacao;

import java.util.List;

public record ChargeDTO(
        String id,
        StatusDoacao status,
        List<LinkDTO> links
) {
}
