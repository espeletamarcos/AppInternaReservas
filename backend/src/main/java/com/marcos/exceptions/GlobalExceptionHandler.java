package com.marcos.exceptions;

import com.marcos.exceptions.recurso.NombreRecursoAlreadyExistsException;
import com.marcos.exceptions.recurso.RecursoNotFoundException;
import com.marcos.exceptions.reserva.*;
import com.marcos.exceptions.usuario.EmailAlreadyExistsException;
import com.marcos.exceptions.usuario.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RecursoNotFoundException.class)
    public ResponseEntity<String> handleRecursoNotFound(RecursoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ReservaNotFoundException.class)
    public ResponseEntity<String> handleReservaNotFound(ReservaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidReservaState.class)
    public ResponseEntity<String> handleInvalidState(InvalidReservaState ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RecursoNotAvailableException.class)
    public ResponseEntity<String> handleRecursoNoDisponible(RecursoNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidReservationDateRangeException.class)
    public ResponseEntity<String> handleInvalidDateRange(InvalidReservationDateRangeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NombreRecursoAlreadyExistsException.class)
    public ResponseEntity<String> handleNombreRecursoAlreadyExists(NombreRecursoAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Catch-all genérico por si algo inesperado falla
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ha ocurrido un error inesperado: " + ex.getMessage());
    }
}
