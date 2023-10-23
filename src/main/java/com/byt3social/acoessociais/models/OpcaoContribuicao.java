package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.OpcaoContribuicaoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "opcoes_contribuicao")
@Entity(name = "OpcaoContribuicao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OpcaoContribuicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double valor;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "acao_id")
    @JsonBackReference
    private AcaoVoluntariado acaoVoluntariado;

    public OpcaoContribuicao(OpcaoContribuicaoDTO opcaoContribuicaoDTO, AcaoVoluntariado acaoVoluntariado) {
        this.valor = opcaoContribuicaoDTO.valor();
        this.descricao = opcaoContribuicaoDTO.descricao();
        this.acaoVoluntariado = acaoVoluntariado;
    }
}
