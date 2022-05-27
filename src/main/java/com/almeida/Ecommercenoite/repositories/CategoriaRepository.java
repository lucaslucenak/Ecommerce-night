package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
}
