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

    public VendaModel(Long idCarrinho, Long idCliente, Long idProduto, TipoPagamentoEnum tipoPagamento, String enderecoEnvio) {
        this.idCarrinho = idCarrinho;
        this.idCliente = idCliente;
        this.idProduto = idProduto;
        this.tipoPagamento = tipoPagamento;
        this.enderecoEnvio = enderecoEnvio;
        this.foiEnviado = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "idCarrinho")
    private Long idCarrinho;

    @Column(name = "idCliente")
    private Long idCliente;

    @Column(name = "idProduto")
    private Long idProduto;

    @Column(name = "tipoPagamento")
    private TipoPagamentoEnum tipoPagamento;

    @Column(name = "enderecoEnvio")
    private String enderecoEnvio;

    @Column(name = "foiEnviado")
    private Boolean foiEnviado;
}
