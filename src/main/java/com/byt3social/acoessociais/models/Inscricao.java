package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.enums.StatusInscricao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "inscricoes")
@Entity(name = "Inscricao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "assinatura_digital")
    @JsonProperty("assinatura_digital")
    private String assinaturaDigital;
    @Enumerated(EnumType.STRING)
    private StatusInscricao status;
    @Column(name = "usuario_id")
    @JsonProperty("usuario_id")
    private Integer participanteId;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "acao_id")
    @JsonBackReference
    @JsonProperty("acao_voluntariado")
    private AcaoVoluntariado acaoVoluntariado;

    public Inscricao(Integer participanteID, AcaoVoluntariado acaoVoluntariado) {
        this.participanteId = participanteID;
        this.acaoVoluntariado = acaoVoluntariado;
        this.status = StatusInscricao.CONFIRMADA;
    }
//
    public void cancelar() {
        this.status = StatusInscricao.CANCELADA;
    }
}
