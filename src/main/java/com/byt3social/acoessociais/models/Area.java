package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "areas")
@Entity(name = "Area")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @OneToMany(mappedBy = "area")
    @JsonBackReference
    private List<AcaoISP> acoesISP;

    public Area(String nome) {
        this.nome = nome;
    }
}
