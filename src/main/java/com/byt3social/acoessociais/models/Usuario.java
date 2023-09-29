package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.ListIterator;

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
    @Column(name = "cadastro_id")
    @JsonProperty("cadastro_id")
    private Integer cadastroId;
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
    @JsonIgnore
    private List<Interesse> interesses = new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    @JsonBackReference
    @JsonProperty("acoes_voluntariado")
    private List<AcaoVoluntariado> acoesVoluntariado = new ArrayList<>();
    @OneToMany(mappedBy = "participante")
    @JsonManagedReference
    @JsonIgnore
    private List<Inscricao> inscricaos = new ArrayList<>();

    public Usuario(UsuarioDTO usuarioDTO) {
        this.nome = usuarioDTO.nome();
        this.email = usuarioDTO.email();
        this.cadastroId = usuarioDTO.id();
    }

    public void removerInteresse(ListIterator<Interesse> interesse) {
        interesse.remove();
    }

    public void adicionarInteresse(Interesse interesse) {
        interesses.add(interesse);
    }
}
