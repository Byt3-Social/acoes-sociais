package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.enums.*;
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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Table(name = "acoes_voluntariado")
@Entity(name = "AcaoVoluntariado")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class AcaoVoluntariado extends Acao {
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
    private String url;
    private Double meta;
    @Column(name = "tipo_meta")
    @JsonProperty("tipo_meta")
    @Enumerated(value = EnumType.STRING)
    private TipoMeta tipoMeta;
    private Boolean campanha;
    private Boolean publica;
    @Column(name = "valor_personalizado")
    @JsonProperty("valor_personalizado")
    private Boolean valorPersonalizado;
    private Integer multiplicador;
    @Column(name = "sobre_organizacao")
    @JsonProperty("sobre_organizacao")
    private String sobreOrganizacao;
    @Column(name = "sobre_acao")
    @JsonProperty("sobre_acao")
    private String sobreAcao;
    @Column(name = "usuario_id")
    @JsonProperty("usuario_id")
    private Integer usuarioId;
    @Column(name = "organizacao_id")
    @JsonProperty("organizacao_id")
    private Integer organizacaoId;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    @OneToOne
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;
    @ManyToOne
    @JoinColumn(name = "segmento_id")
    @JsonManagedReference
    private Segmento segmento;
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<Arquivo> arquivos = new ArrayList<>();
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<Inscricao> inscricoes = new ArrayList<>();
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<Doacao> doacaos;
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<OpcaoContribuicao> opcaoContribuicaos;
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<LocalImpactado> locaisImpactados;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

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
        this.url = toSlug(acaoVoluntariadoDTO.nomeAcao());
        this.meta = acaoVoluntariadoDTO.meta();
        this.tipoMeta = acaoVoluntariadoDTO.tipoMeta();
        this.campanha = acaoVoluntariadoDTO.campanha();
        this.publica = acaoVoluntariadoDTO.publica();
        this.valorPersonalizado = acaoVoluntariadoDTO.valorPersonalizado();
        this.multiplicador = acaoVoluntariadoDTO.multiplicador();
        this.sobreAcao = acaoVoluntariadoDTO.sobreAcao();
        this.sobreOrganizacao = acaoVoluntariadoDTO.sobreOrganizacao();
        this.usuarioId = acaoVoluntariadoDTO.usuarioId();
        this.organizacaoId = acaoVoluntariadoDTO.organizacaoId();
    }

    private static String toSlug(String nomeAcao) {
        String nowhitespace = WHITESPACE.matcher(nomeAcao).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.forLanguageTag("pt-BR"));
    }

    public void vincularSegmento(Segmento segmento) {
        this.segmento = segmento;
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

        if(acaoVoluntariadoDTO.meta() != null) {
            this.meta = acaoVoluntariadoDTO.meta();
        }

        if(acaoVoluntariadoDTO.tipoMeta() != null) {
            this.tipoMeta = acaoVoluntariadoDTO.tipoMeta();
        }

        if(acaoVoluntariadoDTO.campanha() != null) {
            this.campanha = acaoVoluntariadoDTO.campanha();
        }

        if(acaoVoluntariadoDTO.publica() != null) {
            this.publica = acaoVoluntariadoDTO.publica();
        }

        if(acaoVoluntariadoDTO.valorPersonalizado() != null) {
            this.valorPersonalizado = acaoVoluntariadoDTO.valorPersonalizado();
        }

        if(acaoVoluntariadoDTO.multiplicador() != null) {
            this.multiplicador = acaoVoluntariadoDTO.multiplicador();
        }

        if(acaoVoluntariadoDTO.sobreOrganizacao() != null) {
            this.sobreOrganizacao = acaoVoluntariadoDTO.sobreOrganizacao();
        }

        if(acaoVoluntariadoDTO.sobreAcao() != null) {
            this.sobreAcao = acaoVoluntariadoDTO.sobreAcao();
        }

        if(acaoVoluntariadoDTO.organizacaoId() != null) {
            this.organizacaoId = acaoVoluntariadoDTO.organizacaoId();
        }
    }

    public void atualizarSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public void salvarImagem(String caminhoImagem) {
        this.imagem = caminhoImagem;
    }

    public void excluirImagem() {
        this.imagem = null;
    }

    public void incluirContrato(Contrato novoContrato) {
        this.contrato = novoContrato;
    }
}
