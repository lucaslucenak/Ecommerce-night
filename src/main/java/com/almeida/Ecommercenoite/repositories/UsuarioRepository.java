package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
