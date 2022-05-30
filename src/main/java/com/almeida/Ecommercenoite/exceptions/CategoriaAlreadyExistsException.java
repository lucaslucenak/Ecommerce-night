package com.almeida.Ecommercenoite.exceptions;

public class CategoriaAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CategoriaAlreadyExistsException(String msg) {
        super(msg);
    }
}
