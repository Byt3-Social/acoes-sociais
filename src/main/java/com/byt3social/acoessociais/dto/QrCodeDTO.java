package com.byt3social.acoessociais.dto;

import java.util.List;

public record QrCodeDTO(
        String text,
        List<LinkDTO> links
) {
}
