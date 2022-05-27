package com.almeida.Ecommercenoite.entities;

import com.almeida.Ecommercenoite.models.ProdutoModel;
import com.almeida.Ecommercenoite.models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {
    private UsuarioModel usuario;
    private List<ProdutoModel> produtos = new ArrayList<ProdutoModel>();
}
