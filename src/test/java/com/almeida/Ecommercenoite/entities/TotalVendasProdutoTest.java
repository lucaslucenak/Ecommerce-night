package com.almeida.Ecommercenoite.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TotalVendasProdutoTest {

    @Test
    public void shouldIncrementeTotalVendas() {
        TotalVendasProdutos totalVendasProdutos = new TotalVendasProdutos(1L);
        totalVendasProdutos.incrementTotalVendas(50.53);
        Assertions.assertEquals(50.53, totalVendasProdutos.getTotalVendas());
    }

}
