package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "segmentos")
@Entity(name = "Segmento")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Segmento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @OneToMany(mappedBy = "segmento")
    @JsonManagedReference
    private List<Interesse> interesses;
    @OneToMany(mappedBy = "segmento")
    @JsonManagedReference
    @JsonProperty("acoes_voluntariado")
    private List<AcaoVoluntariado> acoesVoluntariado;
}
