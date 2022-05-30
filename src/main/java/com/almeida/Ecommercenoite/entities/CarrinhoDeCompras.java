package com.almeida.Ecommercenoite.entities;

import com.almeida.Ecommercenoite.models.ProdutoModel;
import com.almeida.Ecommercenoite.models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {
    private List<ProdutoModel> produtos = new ArrayList<ProdutoModel>();

    public void addProduto(ProdutoModel produto) {
        this.produtos.add(produto);
    }

    public List<ProdutoModel> getProdutos() {
        return produtos;
    }
}
