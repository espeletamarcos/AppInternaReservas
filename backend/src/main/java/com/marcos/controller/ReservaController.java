package com.marcos.controller;

import com.marcos.dto.reserva.ReservaRequestDTO;
import com.marcos.dto.reserva.ReservaResponseDTO;
import com.marcos.service.reserva.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDTO create (@Valid @RequestBody ReservaRequestDTO reservaRequestDTO) {
        return reservaService.createReserva(reservaRequestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservaResponseDTO findReservaById(@PathVariable Long id) {
        return reservaService.findReservaById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservaResponseDTO> listReservas() {
        return reservaService.listReservas();
    }

    @GetMapping("/usuario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservaResponseDTO> listReservasByUsuario(@PathVariable Long id){
        return reservaService.listReservasByUsuario(id);
    }

    @GetMapping("/recurso/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservaResponseDTO> listReservasByRecurso(@PathVariable Long id){
        return reservaService.listReservasByRecurso(id);
    }

    @PutMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.OK)
    public ReservaResponseDTO cancelarReserva(@PathVariable Long id){
        return reservaService.cancelarReserva(id);
    }
}
