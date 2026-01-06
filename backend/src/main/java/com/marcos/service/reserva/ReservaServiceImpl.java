package com.marcos.service.reserva;

import com.marcos.dto.reserva.ReservaRequestDTO;
import com.marcos.dto.reserva.ReservaResponseDTO;
import com.marcos.exceptions.recurso.RecursoNotFoundException;
import com.marcos.exceptions.reserva.InvalidReservaState;
import com.marcos.exceptions.reserva.InvalidReservationDateRangeException;
import com.marcos.exceptions.reserva.RecursoNotAvailableException;
import com.marcos.exceptions.reserva.ReservaNotFoundException;
import com.marcos.exceptions.usuario.UsuarioNotFoundException;
import com.marcos.model.entity.Recurso;
import com.marcos.model.entity.Reserva;
import com.marcos.model.entity.Usuario;
import com.marcos.model.enums.EstadoReserva;
import com.marcos.repository.RecursoRepository;
import com.marcos.repository.ReservaRepository;
import com.marcos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService{

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RecursoRepository recursoRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, RecursoRepository recursoRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.recursoRepository = recursoRepository;
    }

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
        List<Reserva> listaReservas = reservaRepository.findByRecursoAndFechaInicioLessThanAndFechaFinGreaterThan(recurso, requestDTO.getFechaFin(), requestDTO.getFechaInicio());
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

    @Transactional(readOnly = true)
    @Override
    public ReservaResponseDTO findReservaById(Long id) {
        // 1. Buscar Reserva
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException("La reserva no ha sido encontrada"));
        // 2. Validar que usuario y recurso estén activos
        if (!reserva.getUsuario().isActivo() || !reserva.getRecurso().isActivo()) {
            throw new ReservaNotFoundException("Reserva asociada a usuario o recurso inactivo");
        }
        // 3. Mapear a DTO y devolver
        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .nombreUsuario(reserva.getUsuario().getNombre())
                .nombreRecurso(reserva.getRecurso().getNombre())
                .fechaInicio(reserva.getFechaInicio())
                .fechaFin(reserva.getFechaFin())
                .estadoReserva(reserva.getEstadoReserva())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservaResponseDTO> listReservas() {
        // 1. Obtener la lista de entidades
        List<Reserva> listaReservas = reservaRepository.findAll();
        // 2. Mapear a una List de ResponseDTO
        return listaReservas.stream()
                .filter(reserva -> reserva.getEstadoReserva() == EstadoReserva.ACTIVA || reserva.getEstadoReserva() == EstadoReserva.FINALIZADA)
                .map(reserva -> ReservaResponseDTO.builder()
                        .id(reserva.getId())
                        .nombreUsuario(reserva.getUsuario().getNombre())
                        .nombreRecurso(reserva.getRecurso().getNombre())
                        .fechaInicio(reserva.getFechaInicio())
                        .fechaFin(reserva.getFechaFin())
                        .estadoReserva(reserva.getEstadoReserva())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservaResponseDTO> listReservasByUsuario(Long id) {
        // 1. Obtener usuario o lanzar excepción
        Usuario usuario = usuarioRepository.findById(id).filter(Usuario::isActivo)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        // 2. Obtener lista de entidades por usuario
        List<Reserva> listaReservasByUsuario = reservaRepository.findByUsuario(usuario);
        // 3. Mapear y devolver en DTO
        return listaReservasByUsuario.stream()
                .filter(reserva -> reserva.getEstadoReserva() == EstadoReserva.ACTIVA || reserva.getEstadoReserva() == EstadoReserva.FINALIZADA)
                .map(reserva -> ReservaResponseDTO.builder()
                        .id(reserva.getId())
                        .nombreUsuario(reserva.getUsuario().getNombre())
                        .nombreRecurso(reserva.getRecurso().getNombre())
                        .fechaInicio(reserva.getFechaInicio())
                        .fechaFin(reserva.getFechaFin())
                        .estadoReserva(reserva.getEstadoReserva())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservaResponseDTO> listReservasByRecurso(Long id) {
        // 1. Obtener recurso o lanzar excepción
        Recurso recurso = recursoRepository.findById(id).filter(Recurso::isActivo)
                .orElseThrow(() -> new RecursoNotFoundException("Recurso no encontrado"));
        // 2. Obtener lista de entidades por recurso
        List<Reserva> listaReservasByRecurso = reservaRepository.findByRecurso(recurso);
        // 3. Mapear y devolver en DTO
        return listaReservasByRecurso.stream()
                .filter(reserva -> reserva.getEstadoReserva() == EstadoReserva.ACTIVA || reserva.getEstadoReserva() == EstadoReserva.FINALIZADA)
                .map(reserva -> ReservaResponseDTO.builder()
                        .id(reserva.getId())
                        .nombreUsuario(reserva.getUsuario().getNombre())
                        .nombreRecurso(reserva.getRecurso().getNombre())
                        .fechaInicio(reserva.getFechaInicio())
                        .fechaFin(reserva.getFechaFin())
                        .estadoReserva(reserva.getEstadoReserva())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public ReservaResponseDTO cancelarReserva(Long id) {
        // 1. Buscar Reserva o lanzar excepción
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException("Reserva no encontrada"));
        // 2. Verificar estado o lanzar excepción
        if(reserva.getEstadoReserva() != EstadoReserva.ACTIVA) {
            throw new InvalidReservaState("Sólo se pueden cancelar reservas activas");
        }
        // 3. Modificar estado
        reserva.setEstadoReserva(EstadoReserva.CANCELADA);
        Reserva reservaGuardada = reservaRepository.save(reserva);
        // 4. Devolver DTO
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
