package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    List<ProdutoModel> findProdutoByNomeContains(String nome);

    List<ProdutoModel> findProdutoByIdCategoriaContains(String categoria);
}
