package com.byt3social.acoessociais.models;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name = "doadores")
@Entity(name = "Doador")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonBackReference
    private List<Doacao> doacaos;

    public Doador(DoacaoDTO doacaoDTO, Integer colaboradorId) {
        this.nome = doacaoDTO.nome();
        this.email = doacaoDTO.email();
        this.telefone = doacaoDTO.telefone();
        this.cpf = doacaoDTO.cpf();
        this.usuarioId = colaboradorId;
    }

    public void atualizar(DoacaoDTO doacaoDTO) {
        this.nome = doacaoDTO.nome();
        this.email = doacaoDTO.email();
        this.telefone = doacaoDTO.telefone();
    }
}
