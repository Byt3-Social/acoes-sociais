package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "contratos")
@Entity(name = "Contrato")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "caminho_s3")
    private String caminhoS3;
    private String assinatura;
    @Column(name = "pdsign_processo_id")
    private String pdsignProcessoId;
    @Column(name = "pdsign_documento_id")
    private String pdsignDocumentoId;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToOne(mappedBy = "contrato")
    @JsonBackReference
    private AcaoVoluntariado acaoVoluntariado;
    @OneToOne(mappedBy = "contrato")
    @JsonBackReference
    private AcaoISP acaoISP;

    public Contrato(String caminhoContrato, String processoPdSignId, String documentoPdSignId) {
        this.caminhoS3 = caminhoContrato;
        this.pdsignProcessoId = processoPdSignId;
        this.pdsignDocumentoId = documentoPdSignId;
    }
}
