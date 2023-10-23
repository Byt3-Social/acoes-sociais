package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.MetodoDoacao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DoacaoDTO(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Campo inválido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        String ddd,
        @NotBlank(message = "Campo obrigatório")
        String telefone,
        @NotBlank(message = "Campo obrigatório")
        String cpf,
        MetodoDoacao metodoDoacao,
        EnderecoDTO endereco,
        String tokenCartao,
        String cvv,
        Integer acaoId,
        @NotNull(message = "Escolha um valor")
        Double valorDoacao
) {
}
