    package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.enums.Abrangencia;
import com.byt3social.acoessociais.enums.StatusISP;
import com.byt3social.acoessociais.enums.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "acoes_isp")
@Entity(name = "AcaoISP")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcaoISP extends Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome_acao")
    private String nomeAcao;
    private String descricao;
    @Enumerated(value = EnumType.STRING)
    private Abrangencia abrangencia;
    @Column(name = "tipo_investimento")
    @Enumerated(value = EnumType.STRING)
    private TipoInvestimento tipoInvestimento;
    @Column(name = "qtde_pessoas_impactadas")
    private Integer qtdePessoasImpactadas;
    @Column(name = "aporte_inicial")
    private Double aporteInicial;
    @Enumerated(value = EnumType.STRING)
    private StatusISP status;
    @Column(name = "organizacao_id")
    private Integer organizacaoId;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonManagedReference
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "area_id")
    @JsonManagedReference
    private Area area;
    @ManyToOne
    @JoinColumn(name = "incentivo_id")
    @JsonManagedReference
    private Incentivo incentivo;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "contrato_id")
    @JsonManagedReference
    private Contrato contrato;
    @OneToMany(mappedBy = "acaoISP")
    @JsonManagedReference
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<LocalImpactado> locaisImpactados = new ArrayList<>();
    @OneToMany(mappedBy = "acaoISP")
    @JsonManagedReference
    private List<Aporte> aportes = new ArrayList<>();
    @OneToMany(mappedBy = "acaoISP")
    @JsonManagedReference
    private List<Arquivo> arquivos = new ArrayList<>();

    public AcaoISP(AcaoISPDTO acaoISPDTO, Categoria categoria, Area area, Incentivo incentivo) {
        this.nomeAcao = acaoISPDTO.nomeAcao();
        this.descricao = acaoISPDTO.descricao();
        this.abrangencia = acaoISPDTO.abrangencia();
        this.tipoInvestimento = acaoISPDTO.tipoInvestimento();
        this.qtdePessoasImpactadas = acaoISPDTO.qtdePessoasImpactadas();
        this.aporteInicial = acaoISPDTO.aporteInicial();
        this.status = acaoISPDTO.status();
        this.organizacaoId = acaoISPDTO.organizacaoId();
        this.categoria = categoria;
        this.area = area;
        this.incentivo = incentivo;
    }

    public void adicionarLocaisImpactados(List<LocalImpactado> locaisImpactados) {
        this.locaisImpactados = locaisImpactados;
    }

    public void atualizar(AcaoISPDTO acaoISPDTO, Categoria categoria, Incentivo incentivo, Area area) {
        this.nomeAcao = acaoISPDTO.nomeAcao();
        this.descricao = acaoISPDTO.descricao();
        this.abrangencia = acaoISPDTO.abrangencia();
        this.tipoInvestimento = acaoISPDTO.tipoInvestimento();
        this.qtdePessoasImpactadas = acaoISPDTO.qtdePessoasImpactadas();
        this.aporteInicial = acaoISPDTO.aporteInicial();
        this.status = acaoISPDTO.status();
        this.organizacaoId = acaoISPDTO.organizacaoId();
        this.categoria = categoria;
        this.incentivo = incentivo;
        this.area = area;
    }

    public void incluirContrato(Contrato novoContrato) {
        this.contrato = novoContrato;
    }

    public void excluirContrato() {
        this.contrato = null;
    }
}
