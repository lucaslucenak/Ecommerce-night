package com.almeida.Ecommercenoite.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TotalVendasCategoriaTest {

    @Test
    public void shouldIncrementeTotalVendas() {
        TotalVendasCategoria totalVendasCategoria = new TotalVendasCategoria(1L);
        totalVendasCategoria.incrementTotalVendas(50.53);
        Assertions.assertEquals(50.53, totalVendasCategoria.getTotalVendas());
    }
}
