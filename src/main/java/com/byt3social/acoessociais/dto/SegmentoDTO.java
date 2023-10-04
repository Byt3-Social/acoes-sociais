package com.byt3social.acoessociais.dto;

import jakarta.validation.constraints.NotBlank;

public record SegmentoDTO(
        @NotBlank(message = "O nome do segmento é obrigatório")
        String nome
) {
}
