package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
}
