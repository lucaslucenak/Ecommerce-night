package com.almeida.Ecommercenoite.repositories;

import com.almeida.Ecommercenoite.models.VendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<VendaModel, Long> {

    List<VendaModel> findVendaByIdCliente(Long idCliente);
}
