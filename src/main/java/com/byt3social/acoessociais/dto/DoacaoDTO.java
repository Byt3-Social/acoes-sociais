package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.MetodoDoacao;

import java.util.Date;

public record DoacaoDTO(
        String senderHash,
        String nome,
        String email,
        String ddd,
        String telefone,
        String cpf,
        Date dataNascimento,
        EnderecoDTO endereco,
        MetodoDoacao metodoDoacao,
        String tokenCartao,
        String descricao,
        Integer acaoId,
        Double valorDoacao,
        Integer usuarioId
) {
}
