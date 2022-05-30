package com.almeida.Ecommercenoite.entities;

import com.almeida.Ecommercenoite.models.ProdutoModel;
import com.almeida.Ecommercenoite.models.UsuarioModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private List<ProdutoModel> produtos = new ArrayList<ProdutoModel>();

    public void addProduto(ProdutoModel produto) {
        this.produtos.add(produto);
    }

    public List<ProdutoModel> getProdutos() {
        return produtos;
    }

    public Long getId() {
        return id;
    }
}
