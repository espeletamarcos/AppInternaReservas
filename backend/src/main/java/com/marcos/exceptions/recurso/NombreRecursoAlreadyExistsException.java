package com.marcos.exceptions.recurso;

public class NombreRecursoAlreadyExistsException extends RuntimeException {
    public NombreRecursoAlreadyExistsException(String message) {
        super(message);
    }
}
