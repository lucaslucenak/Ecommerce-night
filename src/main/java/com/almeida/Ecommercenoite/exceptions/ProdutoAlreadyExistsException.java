package com.almeida.Ecommercenoite.exceptions;

public class ProdutoAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ProdutoAlreadyExistsException(String msg) {
        super(msg);
    }
}
