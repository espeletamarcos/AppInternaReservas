package com.marcos.exceptions.recurso;

public class RecursoNotFoundException extends RuntimeException {
    public RecursoNotFoundException(String message) {
        super(message);
    }
}
