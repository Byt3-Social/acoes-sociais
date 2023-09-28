package com.byt3social.acoessociais.dto;

import jakarta.validation.constraints.NotBlank;

public record SegmentoDTO(
        Integer id,
        @NotBlank(message = "O nome do segmento é obrigatório")
        String nome
) {
}
