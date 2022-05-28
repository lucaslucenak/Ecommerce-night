package com.almeida.Ecommercenoite.exceptions;

public class UsernameTakenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameTakenException(String msg) {
        super(msg);
    }
}
