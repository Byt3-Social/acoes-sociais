package com.byt3social.acoessociais.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "categorias")
@Entity(name = "Categoria")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @OneToMany(mappedBy = "categoria")
    @JsonBackReference
    private List<AcaoISP> acoesISP;

    public Categoria(String nomeCategoria) {
        this.nome = nomeCategoria;
    }
}
