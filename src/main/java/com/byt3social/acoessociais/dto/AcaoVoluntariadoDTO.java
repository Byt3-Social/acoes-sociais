package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AcaoVoluntariadoDTO(
        @NotBlank(message = "Campo obrigatório")
        String nomeAcao,
        Nivel nivel,
        @NotNull(message = "Campo obrigatório")
        Fase fase,
        Formato formato,
        Tipo tipo,
        LocalDate dataInicio,
        LocalDate dataTermino,
        String horario,
        String local,
        String informacoesAdicionais,
        Integer vagas,
        Double meta,
        TipoMeta tipoMeta,
        Boolean campanha,
        Boolean publica,
        Boolean valorPersonalizado,
        Integer multiplicador,
        String sobreOrganizacao,
        String sobreAcao,
        List<OpcaoContribuicaoDTO> opcoesContribuicao,
        Integer segmentoID,
        Integer usuarioId,
        Integer organizacaoId
) {
}
