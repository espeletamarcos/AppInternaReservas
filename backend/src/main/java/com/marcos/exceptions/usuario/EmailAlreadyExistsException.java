package com.marcos.exceptions.usuario;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    // opcional: constructor con causa
    public EmailAlreadyExistsException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
