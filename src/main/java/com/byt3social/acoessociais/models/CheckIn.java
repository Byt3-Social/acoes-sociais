package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.enums.TipoCheckIn;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "checkins")
@Entity(name = "CheckIn")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    private Date inicio;
    private Date termino;
    private Integer duracao;
    @Enumerated(EnumType.STRING)
    private TipoCheckIn tipoCheckIn;
    @OneToOne
    @JoinColumn(name = "inscricao_id")
    private Inscricao inscricao;
}
