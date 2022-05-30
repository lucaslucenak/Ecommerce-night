package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.ProdutoModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ProdutoCustomRepository {

    private final EntityManager entityManager;

    public ProdutoCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProdutoModel> getProdutosByIdCategoria(Long idCategoria) {
        String query = "SELECT P FROM tb_produtos as P where P.idCategoria = " + idCategoria;

        var q = entityManager.createNativeQuery(query, ProdutoModel.class);
        q.setParameter("idCategoria", idCategoria);

        return q.getResultList();
    }
}
