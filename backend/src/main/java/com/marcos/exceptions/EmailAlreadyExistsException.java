package com.marcos.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    // opcional: constructor con causa
    public EmailAlreadyExistsException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
