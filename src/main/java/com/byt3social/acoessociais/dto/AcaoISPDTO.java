package com.byt3social.acoessociais.dto;

import com.byt3social.acoessociais.enums.Abrangencia;
import com.byt3social.acoessociais.enums.StatusISP;
import com.byt3social.acoessociais.enums.TipoInvestimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AcaoISPDTO(
        @NotBlank(message = "Campo obrigatório")
        String nomeAcao,
        String descricao,
        Abrangencia abrangencia,
        TipoInvestimento tipoInvestimento,
        Integer qtdePessoasImpactadas,
        Double aporteInicial,
        @NotNull(message = "Campo obrigatório")
        StatusISP status,
        List<String> locaisImpactados,
        List<AporteDTO> aportes,
        @NotNull(message = "Campo obrigatório")
        Integer organizacaoId,
        Integer categoria,
        Integer area,
        Integer incentivo
) {
}
