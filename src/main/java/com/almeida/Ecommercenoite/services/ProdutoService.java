package com.almeida.Ecommercenoite.services;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.models.ProdutoModel;
import com.almeida.Ecommercenoite.repositories.CategoriaRepository;
import com.almeida.Ecommercenoite.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ProdutoService {

    public ProdutoService() {
    }

    @Autowired
    public ProdutoRepository produtoRepository;

    public ProdutoModel createProduto(ProdutoModel produtoModel) {
        return produtoRepository.save(produtoModel);
    }

    public List<ProdutoModel> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<ProdutoModel> getProdutoById(Long id) {
        return produtoRepository.findById(id);
    }

    public void deleteProdutoById(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<ProdutoModel> findProdutoByNome(String nome) {
        return produtoRepository.findProdutoByNomeContains(nome.toUpperCase(Locale.ROOT));
    }
}
