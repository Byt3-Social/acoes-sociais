package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.enums.Abrangencia;
import com.byt3social.acoessociais.enums.StatusISP;
import com.byt3social.acoessociais.enums.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "acoes_isp")
@Entity(name = "AcaoISP")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class AcaoISP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome_acao")
    @JsonProperty("nome_acao")
    private String nomeAcao;
    private String descricao;
    @Enumerated(value = EnumType.STRING)
    private Abrangencia abrangencia;
    @Column(name = "tipo_investimento")
    @JsonProperty("tipo_investimento")
    @Enumerated(value = EnumType.STRING)
    private TipoInvestimento tipoInvestimento;
    @Column(name = "qtde_pessoas_impactadas")
    @JsonProperty("qtde_pessoas_impactadas")
    private Integer qtdePessoasImpactadas;
    @Column(name = "aporte_inicial")
    @JsonProperty("aporte_inicial")
    private Double aporteInicial;
    @Enumerated(value = EnumType.STRING)
    private StatusISP status;
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
    @OneToOne
    @JoinColumn(name = "contrato_id")
    @JsonManagedReference
    private Contrato contrato;
    @OneToMany(mappedBy = "acaoISP")
    @JsonManagedReference
    private List<LocalImpactado> locaisImpactados = new ArrayList<>();
    @OneToMany(mappedBy = "acaoISP")
    @JsonManagedReference
    private List<Aporte> aportes = new ArrayList<>();

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
        if(acaoISPDTO.nomeAcao() != null) {
            this.nomeAcao = acaoISPDTO.nomeAcao();
        }

        if(acaoISPDTO.descricao() != null) {
            this.descricao = acaoISPDTO.descricao();
        }

        if(acaoISPDTO.abrangencia() != null) {
            this.abrangencia = acaoISPDTO.abrangencia();
        }

        if(acaoISPDTO.tipoInvestimento() != null) {
            this.tipoInvestimento = acaoISPDTO.tipoInvestimento();
        }

        if(acaoISPDTO.qtdePessoasImpactadas() != null) {
            this.qtdePessoasImpactadas = acaoISPDTO.qtdePessoasImpactadas();
        }

        if(acaoISPDTO.aporteInicial() != null) {
            this.aporteInicial = acaoISPDTO.aporteInicial();
        }

        if(acaoISPDTO.status() != null) {
            this.status = acaoISPDTO.status();
        }

        this.categoria = categoria;
        this.incentivo = incentivo;
        this.area = area;
    }
}
