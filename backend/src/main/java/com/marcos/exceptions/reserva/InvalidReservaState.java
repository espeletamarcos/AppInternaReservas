package com.marcos.exceptions.reserva;

public class InvalidReservaState extends RuntimeException {
    public InvalidReservaState(String message) {
        super(message);
    }
}
