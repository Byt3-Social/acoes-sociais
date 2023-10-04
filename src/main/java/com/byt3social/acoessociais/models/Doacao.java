package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.enums.StatusDoacao;
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

@Table(name = "doacoes")
@Entity(name = "Doacao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
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
    @JsonBackReference
    private Doador doador;

    public Doacao(DoacaoDTO doacaoDTO, AcaoVoluntariado acaoVoluntariado, Doador doador) {
        this.metodoDoacao = doacaoDTO.metodoDoacao();
        this.valor = doacaoDTO.valorDoacao();
        this.statusDoacao = StatusDoacao.CADASTRADA;
        this.acaoVoluntariado = acaoVoluntariado;
        this.doador = doador;
    }

    public void vincularTransacaoPagseguro(String transacaoID) {
        this.codigo = transacaoID;
    }

    public void atualizarStatus(PagseguroTransacaoDTO pagseguroTransacaoDTO) {
        this.statusDoacao = converterStatusDoacao(pagseguroTransacaoDTO.status());
    }

    private StatusDoacao converterStatusDoacao(Integer status) {
        if(status == 1) {
            return StatusDoacao.AGUARDANDO_PAGAMENTO;
        } else if(status == 2) {
            return StatusDoacao.EM_ANALISE;
        } else if(status == 3) {
            return StatusDoacao.PAGA;
        } else if(status == 6) {
            return StatusDoacao.DEVOLVIDA;
        } else if(status == 7) {
            return StatusDoacao.CANCELADA;
        } else {
            return this.statusDoacao;
        }
    }

    public void atualizarLinkPagamento(String linkPagamento) {
        this.link = linkPagamento;
    }
}
