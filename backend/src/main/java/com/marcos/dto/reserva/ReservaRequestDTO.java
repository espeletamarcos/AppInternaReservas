package com.marcos.dto.reserva;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaRequestDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long recursoId;

    @NotNull
    @FutureOrPresent
    private LocalDateTime fechaInicio;

    @NotNull
    @Future
    private LocalDateTime fechaFin;
}
