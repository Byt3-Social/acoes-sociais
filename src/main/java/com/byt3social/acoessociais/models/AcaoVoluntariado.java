package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.enums.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Table(name = "acoes_voluntariado")
@Entity(name = "AcaoVoluntariado")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcaoVoluntariado extends Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome_acao")
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
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataInicio;
    @Column(name = "data_termino")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataTermino;
    @JsonFormat(pattern="HH:mm")
    private LocalTime horario;
    private String local;
    @Column(name = "informacoes_adicionais")
    private String informacoesAdicionais;
    private String imagem;
    private Integer vagas;
    private String url;
    private Double meta;
    @Column(name = "tipo_meta")
    @Enumerated(value = EnumType.STRING)
    private TipoMeta tipoMeta;
    private Boolean campanha;
    private Boolean publica;
    @Column(name = "valor_personalizado")
    private Boolean valorPersonalizado;
    private Integer multiplicador;
    @Column(name = "sobre_organizacao")
    private String sobreOrganizacao;
    @Column(name = "sobre_acao")
    private String sobreAcao;
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @Column(name = "organizacao_id")
    private Integer organizacaoId;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToOne(orphanRemoval = true)
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
    @OrderBy(value = "createdAt DESC")
    private List<Doacao> doacaos = new ArrayList<>();
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<OpcaoContribuicao> opcoesContribuicao = new ArrayList<>();
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<LocalImpactado> locaisImpactados = new ArrayList<>();

    public AcaoVoluntariado(AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        this.nomeAcao = acaoVoluntariadoDTO.nomeAcao();
        this.nivel = acaoVoluntariadoDTO.nivel();
        this.fase = acaoVoluntariadoDTO.fase();
        this.formato = acaoVoluntariadoDTO.formato();
        this.tipo = acaoVoluntariadoDTO.tipo();
        this.dataInicio = acaoVoluntariadoDTO.dataInicio();
        this.dataTermino = acaoVoluntariadoDTO.dataTermino();
        this.horario = acaoVoluntariadoDTO.horario() != null ? LocalTime.parse(acaoVoluntariadoDTO.horario()) : null;
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

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

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
        this.nomeAcao = acaoVoluntariadoDTO.nomeAcao();
        this.nivel = acaoVoluntariadoDTO.nivel();
        this.fase = acaoVoluntariadoDTO.fase();
        this.formato = acaoVoluntariadoDTO.formato();
        this.tipo = acaoVoluntariadoDTO.tipo();
        this.dataInicio = acaoVoluntariadoDTO.dataInicio();
        this.dataTermino = acaoVoluntariadoDTO.dataTermino();
        this.horario = acaoVoluntariadoDTO.horario() != null ? LocalTime.parse(acaoVoluntariadoDTO.horario()) : null;
        this.local = acaoVoluntariadoDTO.local();
        this.informacoesAdicionais = acaoVoluntariadoDTO.informacoesAdicionais();
        this.vagas = acaoVoluntariadoDTO.vagas();
        this.meta = acaoVoluntariadoDTO.meta();
        this.tipoMeta = acaoVoluntariadoDTO.tipoMeta();
        this.campanha = acaoVoluntariadoDTO.campanha();
        this.publica = acaoVoluntariadoDTO.publica();
        this.valorPersonalizado = acaoVoluntariadoDTO.valorPersonalizado();
        this.multiplicador = acaoVoluntariadoDTO.multiplicador();
        this.sobreOrganizacao = acaoVoluntariadoDTO.sobreOrganizacao();
        this.sobreAcao = acaoVoluntariadoDTO.sobreAcao();
        this.organizacaoId = acaoVoluntariadoDTO.organizacaoId();
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

    public void excluirContrato() {
        this.contrato = null;
    }
}
