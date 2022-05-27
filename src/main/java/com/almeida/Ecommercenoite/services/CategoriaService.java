package com.almeida.Ecommercenoite.services;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.repositories.CategoriaRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

    public List<CategoriaModel> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<CategoriaModel> getCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    public void deleteCategoriaById(Long id) {
        categoriaRepository.deleteById(id);
    }

    public List<CategoriaModel> findCategoriaByNome(String nome) {
        return categoriaRepository.findCategoriaByNomeContains(nome.toUpperCase(Locale.ROOT));
    }
}
