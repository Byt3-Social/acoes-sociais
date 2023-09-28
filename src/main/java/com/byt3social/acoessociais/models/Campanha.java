package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.CampanhaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "campanhas")
@Entity(name = "Campanha")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Campanha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer meta;
    @Column(name = "meta_descricao")
    @JsonProperty("meta_descricao")
    private String  metaDescricao;
    private String url;
    @OneToOne(mappedBy = "campanha")
    @JsonProperty("acao_voluntariado")
    @JsonBackReference
    private AcaoVoluntariado acaoVoluntariado;
    @OneToOne(mappedBy = "campanha")
    private Contrato contrato;

    public Campanha(CampanhaDTO campanhaDTO, AcaoVoluntariado acaoVoluntariado) {
        this.meta = campanhaDTO.meta();
        this.metaDescricao = campanhaDTO.metaDescricao();
        this.url = UUID.randomUUID().toString();
        this.acaoVoluntariado = acaoVoluntariado;
    }

    public void atualizar(CampanhaDTO campanhaDTO) {
        if(campanhaDTO.meta() != null) {
            this.meta = campanhaDTO.meta();
        }

        if(campanhaDTO.metaDescricao() != null) {
            this.metaDescricao = campanhaDTO.metaDescricao();
        }
    }
}
