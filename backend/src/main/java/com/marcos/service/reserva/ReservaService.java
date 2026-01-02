package com.marcos.service.reserva;

import com.marcos.dto.reserva.ReservaRequestDTO;
import com.marcos.dto.reserva.ReservaResponseDTO;

import java.util.List;

public interface ReservaService {

    ReservaResponseDTO createReserva(ReservaRequestDTO requestDTO);
    ReservaResponseDTO findReservaById(Long id);
    List<ReservaResponseDTO> listReservas();
    List<ReservaResponseDTO> listReservasByUsuario(Long id);
    List<ReservaResponseDTO> listReservasByRecurso(Long id);
    ReservaResponseDTO cancelarReserva(Long id);
}
