package com.marcos.dto.reserva;

import com.marcos.model.enums.EstadoReserva;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaResponseDTO {

    private Long id;
    private String nombreUsuario;
    private String nombreRecurso;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EstadoReserva estadoReserva;
}
