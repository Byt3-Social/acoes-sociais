package com.byt3social.acoessociais.dto;

public record EnderecoDTO(
        String endereco,
        String numero,
        String bairro,
        String complemento,
        String cidade,
        String estado,
        String cep
) {
}
