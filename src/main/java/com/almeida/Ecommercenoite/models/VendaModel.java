package com.almeida.Ecommercenoite.models;


import com.almeida.Ecommercenoite.enums.TipoPagamentoEnum;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_vendas")
public class VendaModel {

    public VendaModel(Long idCliente, Long idProduto, Long idCategoria, TipoPagamentoEnum tipoPagamento, String enderecoEnvio, Double valorVenda) {
        this.idCliente = idCliente;
        this.idProduto = idProduto;
        this.idCategoria = idCategoria;
        this.tipoPagamento = tipoPagamento;
        this.enderecoEnvio = enderecoEnvio;
        this.valorVenda = valorVenda;
        this.foiEnviado = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "idCliente")
    private Long idCliente;

    @Column(name = "idProduto")
    private Long idProduto;

    @Column(name = "idCategoria")
    private Long idCategoria;

    @Column(name = "tipoPagamento")
    private TipoPagamentoEnum tipoPagamento;

    @Column(name = "enderecoEnvio")
    private String enderecoEnvio;

    @Column(name = "valorVenda")
    private Double valorVenda;

    @Column(name = "foiEnviado")
    private Boolean foiEnviado;
}
