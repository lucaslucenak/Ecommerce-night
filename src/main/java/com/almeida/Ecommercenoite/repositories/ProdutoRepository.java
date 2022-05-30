package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    List<ProdutoModel> findProdutoByNomeContains(String nome);

    List<ProdutoModel> findProdutoByIdCategoriaContains(Long idCategoria);
}
