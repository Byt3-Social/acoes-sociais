package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.Abrangencia;
import com.byt3social.acoessociais.enums.StatusISP;
import com.byt3social.acoessociais.enums.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AcaoISPDTO(
        @JsonProperty("nome_acao")
        String nomeAcao,
        String descricao,
        Abrangencia abrangencia,
        @JsonProperty("tipo_investimento")
        TipoInvestimento tipoInvestimento,
        @JsonProperty("qtde_pessoas_impactadas")
        Integer qtdePessoasImpactadas,
        @JsonProperty("aporte_inicial")
        Double aporteInicial,
        StatusISP status,
        @JsonProperty("locais_impactados")
        List<String> locaisImpactados,
        List<AporteDTO> aportes,
        @JsonProperty("organizacao_id")
        Integer organizacaoId,
        @JsonProperty("categoria_id")
        Integer categoria,
        @JsonProperty("area_id")
        Integer area,
        @JsonProperty("incentivo_id")
        Integer incentivo
) {
}
