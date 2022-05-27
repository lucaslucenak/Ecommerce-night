package com.almeida.Ecommercenoite.models;


import lombok.*;

import javax.persistence.*;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_produtos")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "idCategoria")
    private Long idCategoria;

    public ProdutoModel(String nome) {
        this.nome = nome.toUpperCase(Locale.ROOT);
    }
}
