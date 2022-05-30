package com.almeida.Ecommercenoite.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalVendasProdutos {

    public TotalVendasProdutos(Long idProduto) {
        this.idProduto = idProduto;
        this.totalVendas = 0.0;
    }

    public TotalVendasProdutos() {
    }
    private Long idProduto;
    private String nomeProduto;
    private Double totalVendas;

    public void incrementTotalVendas(Double value) {
        this.totalVendas += value;
    }
}
