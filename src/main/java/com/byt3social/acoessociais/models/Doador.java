package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Table(name = "doadores")
@Entity(name = "Doador")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Doador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    @Column(name = "data_nascimento")
    @JsonProperty("data_nascimento")
    private Date dataNascimento;
    @Column(name = "usuario_id")
    @JsonProperty("usuario_id")
    private Integer usuarioId;
    @OneToMany(mappedBy = "doador")
    @JsonManagedReference
    private List<Doacao> doacaos;

    public Doador(DoacaoDTO doacaoDTO) {
        this.nome = doacaoDTO.nome();
        this.email = doacaoDTO.email();
        this.telefone = doacaoDTO.telefone();
        this.cpf = doacaoDTO.cpf();
        this.dataNascimento = doacaoDTO.dataNascimento();
        this.usuarioId = doacaoDTO.usuarioId();
    }

    public void atualizar(DoacaoDTO doacaoDTO) {
        if(doacaoDTO.nome() != null) {
            this.nome = doacaoDTO.nome();
        }

        if(doacaoDTO.email() != null) {
            this.email = doacaoDTO.email();
        }

        if(doacaoDTO.telefone() != null) {
            this.telefone = doacaoDTO.telefone();
        }
    }
}
