package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.enums.Fase;
import com.byt3social.acoessociais.enums.Formato;
import com.byt3social.acoessociais.enums.Nivel;
import com.byt3social.acoessociais.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @OneToOne
    @JoinColumn(name = "campanha_id")
    private Campanha campanha;
    @ManyToOne
    @JoinColumn(name = "segmento_id")
    @JsonBackReference
    private Segmento segmento;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "organizacao_id")
    @JsonBackReference
    private Organizacao organizacao;
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<Arquivo> arquivos;
    @OneToMany(mappedBy = "acaoVoluntariado")
    @JsonManagedReference
    private List<Inscricao> inscricoes;
}
