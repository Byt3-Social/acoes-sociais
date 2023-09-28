package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.dto.CampanhaDTO;
import com.byt3social.acoessociais.enums.Fase;
import com.byt3social.acoessociais.enums.Formato;
import com.byt3social.acoessociais.enums.Nivel;
import com.byt3social.acoessociais.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "acoes_voluntariado")
@Entity(name = "AcaoVoluntariado")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class AcaoVoluntariado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome_acao")
    @JsonProperty("nome_acao")
    private String nomeAcao;
    @Enumerated(EnumType.STRING)
    private Nivel nivel;
    @Enumerated(EnumType.STRING)
    private Fase fase;
    @Enumerated(EnumType.STRING)
    private Formato formato;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Column(name = "data_inicio")
    @JsonProperty("data_inicio")
    private Date dataInicio;
    @Column(name = "data_termino")
    @JsonProperty("data_termino")
    private Date dataTermino;
    private Time horario;
    private String local;
    @Column(name = "informacoes_adicionais")
    @JsonProperty("informacoes_adicionais")
    private String informacoesAdicionais;
    private String imagem;
    private Integer vagas;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "campanha_id")
    @JsonManagedReference
    private Campanha campanha;
    @ManyToOne
    @JoinColumn(name = "segmento_id")
    @JsonManagedReference
    private Segmento segmento;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonManagedReference
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "organizacao_id")
    @JsonManagedReference
    private Organizacao organizacao;
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<Arquivo> arquivos = new ArrayList<>();
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<Inscricao> inscricoes = new ArrayList<>();

    public AcaoVoluntariado(AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        this.nomeAcao = acaoVoluntariadoDTO.nomeAcao();
        this.nivel = acaoVoluntariadoDTO.nivel();
        this.fase = acaoVoluntariadoDTO.fase();
        this.formato = acaoVoluntariadoDTO.formato();
        this.tipo = acaoVoluntariadoDTO.tipo();
        this.dataInicio = acaoVoluntariadoDTO.dataInicio();
        this.dataTermino = acaoVoluntariadoDTO.dataTermino();
        this.horario = acaoVoluntariadoDTO.horario();
        this.local = acaoVoluntariadoDTO.local();
        this.informacoesAdicionais = acaoVoluntariadoDTO.informacoesAdicionais();
        this.vagas = acaoVoluntariadoDTO.vagas();
    }

    public void criarCampanha(CampanhaDTO campanhaDTO) {
        Campanha campanha = new Campanha(campanhaDTO, this);

        this.campanha = campanha;
    }

    public void vincularSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public void vincularUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void vincularOrganizacao(Organizacao organizacao) {
        this.organizacao = organizacao;
    }

    public void atualizar(AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        if(acaoVoluntariadoDTO.nomeAcao() != null) {
            this.nomeAcao = acaoVoluntariadoDTO.nomeAcao();
        }

        if(acaoVoluntariadoDTO.nivel() != null) {
            this.nivel = acaoVoluntariadoDTO.nivel();
        }

        if(acaoVoluntariadoDTO.fase() != null) {
            this.fase = acaoVoluntariadoDTO.fase();
        }

        if(acaoVoluntariadoDTO.formato() != null) {
            this.formato = acaoVoluntariadoDTO.formato();
        }

        if(acaoVoluntariadoDTO.tipo() != null) {
            this.tipo = acaoVoluntariadoDTO.tipo();
        }

        if(acaoVoluntariadoDTO.dataInicio() != null) {
            this.dataInicio = acaoVoluntariadoDTO.dataInicio();
        }

        if(acaoVoluntariadoDTO.dataTermino() != null) {
            this.dataTermino = acaoVoluntariadoDTO.dataTermino();
        }

        if(acaoVoluntariadoDTO.horario() != null) {
            this.horario = acaoVoluntariadoDTO.horario();
        }

        if(acaoVoluntariadoDTO.local() != null) {
            this.local = acaoVoluntariadoDTO.local();
        }

        if(acaoVoluntariadoDTO.informacoesAdicionais() != null) {
            this.informacoesAdicionais = acaoVoluntariadoDTO.informacoesAdicionais();
        }

        if(acaoVoluntariadoDTO.vagas() != null) {
            this.vagas = acaoVoluntariadoDTO.vagas();
        }

        if(acaoVoluntariadoDTO.campanha() != null) {
            if(this.campanha != null) {
                this.campanha.atualizar(acaoVoluntariadoDTO.campanha());
            } else {
                this.campanha = new Campanha(acaoVoluntariadoDTO.campanha(), this);
            }
        }
    }

    public void atualizarSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public void atualizarOrganizacao(Organizacao organizacao) {
        this.organizacao = organizacao;
    }
}
