package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private AcaoVoluntariado acaoVoluntariado;
    @OneToOne(mappedBy = "campanha")
    private Contrato contrato;
}
