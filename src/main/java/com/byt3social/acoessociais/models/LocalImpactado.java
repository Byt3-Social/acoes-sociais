package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "locais_impactados")
@Entity(name = "LocalImpactado")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocalImpactado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
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
