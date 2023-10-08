package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.MetodoDoacao;

public record DoacaoDTO(
        String nome,
        String email,
        String ddd,
        String telefone,
        String cpf,
        MetodoDoacao metodoDoacao,
        EnderecoDTO endereco,
        String tokenCartao,
        String cvv,
        Integer acaoId,
        Double valorDoacao,
        Integer usuarioId
) {
}
