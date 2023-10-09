package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "locais_impactados")
@Entity(name = "LocalImpactado")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class LocalImpactado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String local;
    @ManyToOne
    @JoinColumn(name = "isp_id")
    @JsonBackReference
    private AcaoISP acaoISP;
    @ManyToOne
    @JoinColumn(name = "voluntariado_id")
    @JsonBackReference
    private AcaoVoluntariado acaoVoluntariado;

    public LocalImpactado(String localImpactado, AcaoISP acaoISP) {
        this.local = localImpactado;
        this.acaoISP = acaoISP;
    }
}
