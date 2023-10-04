package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "contratos")
@Entity(name = "Contrato")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "caminho_s3")
    @JsonProperty("caminho_s3")
    private String caminhoS3;
    private String assinatura;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    @OneToOne(mappedBy = "contrato")
    private AcaoVoluntariado acaoVoluntariado;
}
