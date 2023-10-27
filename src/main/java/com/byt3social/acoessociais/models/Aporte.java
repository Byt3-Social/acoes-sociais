package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.AporteDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "aportes")
@Entity(name = "Aporte")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Aporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double valor;
    private Date data;
    @ManyToOne
    @JoinColumn(name = "isp_id")
    @JsonBackReference
    private AcaoISP acaoISP;

    public Aporte(AporteDTO aporteDTO, AcaoISP acaoISP) {
        this.valor = aporteDTO.valor();
        this.data = aporteDTO.data();
        this.acaoISP = acaoISP;
    }
}
