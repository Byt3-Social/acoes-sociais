package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.enums.StatusInscricao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "inscricoes")
@Entity(name = "Inscricao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
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
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario participante;
    @ManyToOne
    @JoinColumn(name = "acao_id")
    @JsonBackReference
    @JsonProperty("acao_voluntariado")
    private AcaoVoluntariado acaoVoluntariado;
    @OneToOne(mappedBy = "inscricao")
    private CheckIn checkIn;

    public Inscricao(Usuario participante, AcaoVoluntariado acaoVoluntariado) {
        this.participante = participante;
        this.acaoVoluntariado = acaoVoluntariado;
        this.status = StatusInscricao.CONFIRMADA;
    }

    public void cancelar() {
        this.status = StatusInscricao.CANCELADA;
    }
}
