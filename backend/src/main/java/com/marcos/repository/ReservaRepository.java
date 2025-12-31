package com.marcos.repository;

import com.marcos.model.entity.Recurso;
import com.marcos.model.entity.Reserva;
import com.marcos.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuario (Usuario usuario);

    List<Reserva> findByRecurso (Recurso recurso);

    // Para validar solapamientos
    List<Reserva> findByRecursoAndFechaInicioLessThanAndFechaFinGreaterThan (Recurso recurso, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
