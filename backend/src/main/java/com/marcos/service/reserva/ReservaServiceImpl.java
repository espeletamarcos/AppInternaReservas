package com.marcos.service.reserva;

import com.marcos.dto.reserva.ReservaRequestDTO;
import com.marcos.dto.reserva.ReservaResponseDTO;
import com.marcos.exceptions.recurso.RecursoNotFoundException;
import com.marcos.exceptions.reserva.InvalidReservationDateRangeException;
import com.marcos.exceptions.reserva.RecursoNotAvailableException;
import com.marcos.exceptions.usuario.UsuarioNotFoundException;
import com.marcos.model.entity.Recurso;
import com.marcos.model.entity.Reserva;
import com.marcos.model.entity.Usuario;
import com.marcos.model.enums.EstadoReserva;
import com.marcos.repository.RecursoRepository;
import com.marcos.repository.ReservaRepository;
import com.marcos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ReservaServiceImpl implements ReservaService{

    // todo Hacerlo con constructor
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Transactional
    @Override
    public ReservaResponseDTO createReserva(ReservaRequestDTO requestDTO) {
        // 1. Validar fechas
        if (!(requestDTO.getFechaInicio().isBefore(requestDTO.getFechaFin()))) {
            throw new InvalidReservationDateRangeException("La fecha de inicio tiene que ser anterior a la fecha de finalización");
        }
        // 2. Buscar Usuario
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId()).filter(Usuario::isActivo)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        // 3. Buscar Recurso
        Recurso recurso = recursoRepository.findById(requestDTO.getRecursoId()).filter(Recurso::isActivo)
                .orElseThrow(() -> new RecursoNotFoundException("Recurso no encontrado"));
        // 4. Validar Disponibilidad
        List<Reserva> listaReservas = reservaRepository.findByRecursoAndFechaInicioLessThanAndFechaFinGreaterThan(recurso, requestDTO.getFechaInicio(), requestDTO.getFechaFin());
        if(!listaReservas.isEmpty()) {
            throw new RecursoNotAvailableException("El recurso no está disponible para las fechas seleccionadas");
        }
        // 5. Crear Reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setRecurso(recurso);
        reserva.setFechaInicio(requestDTO.getFechaInicio());
        reserva.setFechaFin(requestDTO.getFechaFin());
        reserva.setEstadoReserva(EstadoReserva.ACTIVA);
        // 6. Guardar en BD
        Reserva reservaGuardada = reservaRepository.save(reserva);
        // 7. Mapear a ReservaResponseDTO
        return ReservaResponseDTO.builder()
                .id(reservaGuardada.getId())
                .nombreUsuario(reservaGuardada.getUsuario().getNombre())
                .nombreRecurso(reservaGuardada.getRecurso().getNombre())
                .fechaInicio(reservaGuardada.getFechaInicio())
                .fechaFin(reservaGuardada.getFechaFin())
                .estadoReserva(reservaGuardada.getEstadoReserva())
                .build();
    }
}
