package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "organizacoes")
@Entity(name = "Organizacao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Organizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
    @Column(name = "nome_empresarial")
    @JsonProperty("nome_empresarial")
    private String nomeEmpresarial;
    @Column(name = "cadastro_id")
    @JsonProperty("cadastro_id")
    private Integer cadastroId;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    @OneToMany(mappedBy = "organizacao")
    @JsonProperty("acoes_voluntariado")
    @JsonManagedReference
    private List<AcaoVoluntariado> acoesVoluntariado;
}
