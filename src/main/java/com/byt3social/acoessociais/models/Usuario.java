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

@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    @CreationTimestamp
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Interesse> interesses;
    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    @JsonProperty("acoes_voluntariado")
    private List<AcaoVoluntariado> acoesVoluntariado;
    @OneToMany(mappedBy = "participante")
    @JsonManagedReference
    private List<Inscricao> inscricaos;
}
