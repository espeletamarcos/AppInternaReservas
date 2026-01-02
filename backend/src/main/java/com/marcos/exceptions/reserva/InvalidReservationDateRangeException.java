package com.marcos.exceptions.reserva;

public class InvalidReservationDateRangeException extends RuntimeException {
    public InvalidReservationDateRangeException(String message) {
        super(message);
    }
}
