package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "interesses")
@Entity(name = "Interesse")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Interesse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "usuario_id")
    @JsonProperty("usuario_id")
    private Integer usuarioId;
    @ManyToOne
    @JoinColumn(name = "segmento_id")
    @JsonManagedReference
    private Segmento segmento;

    public Interesse(Segmento segmento, Integer usuarioID) {
        this.usuarioId = usuarioID;
        this.segmento = segmento;
    }
}
