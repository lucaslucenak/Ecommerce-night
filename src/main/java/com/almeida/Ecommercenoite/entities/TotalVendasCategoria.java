package com.almeida.Ecommercenoite.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalVendasCategoria {

    public TotalVendasCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
        this.totalVendas = 0.0;
    }

    public TotalVendasCategoria() {
    }
    private Long idCategoria;
    private String nomeCategoria;
    private Double totalVendas;

    public void incrementTotalVendas(Double value) {
        this.totalVendas += value;
    }
}
