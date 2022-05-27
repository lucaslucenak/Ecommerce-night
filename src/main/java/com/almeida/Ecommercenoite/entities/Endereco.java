package com.almeida.Ecommercenoite.entities;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String numero;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
