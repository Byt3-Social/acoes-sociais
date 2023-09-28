package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private Segmento segmento;

    public Interesse(Segmento segmento, Usuario usuario) {
        this.usuario = usuario;
        this.segmento = segmento;
    }
}
