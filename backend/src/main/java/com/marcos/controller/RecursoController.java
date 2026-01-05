package com.marcos.controller;

import com.marcos.dto.recurso.RecursoRequestDTO;
import com.marcos.dto.recurso.RecursoResponseDTO;
import com.marcos.service.recurso.RecursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecursoResponseDTO create (@Valid @RequestBody RecursoRequestDTO recursoRequestDTO) {
        return recursoService.createRecurso(recursoRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecursoResponseDTO> listRecursos() {
        return recursoService.listRecursos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecursoResponseDTO findRecursoById(@PathVariable Long id) {
        return recursoService.findRecursoById(id);
    }
}
