package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.SegmentoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "segmentos")
@Entity(name = "Segmento")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Segmento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @OneToMany(mappedBy = "segmento")
    @JsonBackReference
    private List<Interesse> interesses = new ArrayList<>();
    @OneToMany(mappedBy = "segmento")
    @JsonBackReference
    @JsonProperty("acoes_voluntariado")
    private List<AcaoVoluntariado> acoesVoluntariado = new ArrayList<>();

    public Segmento(SegmentoDTO segmentoDTO) {
        this.nome = segmentoDTO.nome();
    }
}
