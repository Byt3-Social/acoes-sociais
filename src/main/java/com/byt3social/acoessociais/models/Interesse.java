package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "interesses")
@Entity(name = "Interesse")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Interesse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "segmento_id")
    @JsonBackReference
    private Segmento segmento;
}
