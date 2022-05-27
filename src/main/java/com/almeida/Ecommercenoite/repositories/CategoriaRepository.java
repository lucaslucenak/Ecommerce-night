package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

    List<CategoriaModel> findCategoriaByNomeContains(String nome);
}
