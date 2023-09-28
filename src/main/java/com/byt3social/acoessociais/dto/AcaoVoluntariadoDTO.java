package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.Fase;
import com.byt3social.acoessociais.enums.Formato;
import com.byt3social.acoessociais.enums.Nivel;
import com.byt3social.acoessociais.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Date;

public record AcaoVoluntariadoDTO(
        Integer id,
        @JsonProperty("nome_acao")
        @NotBlank(message = "O nome da ação é obrigatório")
        String nomeAcao,
        Nivel nivel,
        @NotNull(message = "A fase da ação é obrigatório")
        Fase fase,
        Formato formato,
        Tipo tipo,
        @JsonProperty("data_inicio")
        Date dataInicio,
        @JsonProperty("data_termino")
        Date dataTermino,
        Time horario,
        String local,
        @JsonProperty("informacoes_adicionais")
        String informacoesAdicionais,
        Integer vagas,
        CampanhaDTO campanha,
        @JsonProperty("segmento_id")
        Integer segmentoID,
        @JsonProperty("usuario_id")
        @NotNull(message = "O usuário é obrigatório")
        Integer usuarioID,
        @JsonProperty("organizacao_id")
        Integer organizacaoID
) {
}
