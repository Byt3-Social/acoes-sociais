package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.LinkDTO;
import com.byt3social.acoessociais.dto.PagseguroCancelamentoDTO;
import com.byt3social.acoessociais.dto.QrCodeDTO;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.enums.StatusDoacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "doacoes")
@Entity(name = "Doacao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String codigo;
    @Column(name = "metodo")
    @JsonProperty("metodo")
    @Enumerated(value = EnumType.STRING)
    private MetodoDoacao metodoDoacao;
    private Double valor;
    private String link;
    @Column(name = "status")
    @JsonProperty("status")
    @Enumerated(value = EnumType.STRING)
    private StatusDoacao statusDoacao;
    @Column(name = "qrcode_text")
    private String qrcodeText;
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
    private AcaoVoluntariado acaoVoluntariado;
    @ManyToOne
    @JoinColumn(name = "doador_id")
    @JsonManagedReference
    private Doador doador;

    public Doacao(DoacaoDTO doacaoDTO, AcaoVoluntariado acaoVoluntariado, Doador doador) {
        this.metodoDoacao = doacaoDTO.metodoDoacao();
        this.valor = doacaoDTO.valorDoacao();
        this.statusDoacao = StatusDoacao.CREATED;
        this.acaoVoluntariado = acaoVoluntariado;
        this.doador = doador;
    }

    public void vincularTransacaoPagseguro(String transacaoID) {
        this.codigo = transacaoID;
    }

    public void atualizarStatus(StatusDoacao statusDoacao) {
        this.statusDoacao = statusDoacao;
    }

    public void atualizarLinkPagamentoPix(QrCodeDTO qrCodeDTO) {
        LinkDTO linkDTO = qrCodeDTO.links().stream().filter(link -> link.media().equals("image/png")).findFirst().get();

        this.qrcodeText = qrCodeDTO.text();
        this.link = linkDTO.href();
    }

    public void atualizarLinkPagamentoBoleto(List<LinkDTO> links) {
        LinkDTO linkDTO = links.stream().filter(link -> link.media().equals("application/pdf")).findFirst().get();

        this.link = linkDTO.href();
    }

    public void atualizarIdentificador(String identificadorTransacao) {
        this.codigo = identificadorTransacao;
    }

    public void cancelar(PagseguroCancelamentoDTO pagseguroCancelamentoDTO) {
        this.statusDoacao = pagseguroCancelamentoDTO.status();
    }
}
