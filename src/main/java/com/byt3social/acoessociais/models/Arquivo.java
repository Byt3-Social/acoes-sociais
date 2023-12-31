package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "arquivos")
@Entity(name = "Arquivo")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "caminho_s3")
    private String caminhoS3;
    @Column(name = "nome_arquivo_original")
    private String nomeArquivoOriginal;
    private Long tamanho;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "voluntariado_id")
    @JsonBackReference
    private AcaoVoluntariado acaoVoluntariado;
    @ManyToOne
    @JoinColumn(name = "isp_id")
    @JsonBackReference
    private AcaoISP acaoISP;

    public Arquivo(String caminhoArquivo, String nomeDocumento, Long tamanhoDocumento, Acao acao) {
        this.caminhoS3 = caminhoArquivo;
        this.nomeArquivoOriginal = nomeDocumento;
        this.tamanho = tamanhoDocumento;

        if(acao instanceof AcaoVoluntariado) {
            this.acaoVoluntariado = (AcaoVoluntariado) acao;
        } else {
            this.acaoISP = (AcaoISP) acao;
        }
    }
}
