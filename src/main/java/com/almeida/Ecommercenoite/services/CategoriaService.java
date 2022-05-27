package com.almeida.Ecommercenoite.services;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.repositories.CategoriaRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class CategoriaService {

    public CategoriaService() {
    }

    @Autowired
    public CategoriaRepository categoriaRepository;

    public CategoriaModel createCategoria(CategoriaModel categoriaModel) {
        return categoriaRepository.save(categoriaModel);
    }
}
