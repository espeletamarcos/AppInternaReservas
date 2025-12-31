package com.marcos.service.recurso;

import com.marcos.dto.recurso.RecursoRequestDTO;
import com.marcos.dto.recurso.RecursoResponseDTO;

import java.util.List;

public interface RecursoService {

    RecursoResponseDTO createRecurso(RecursoRequestDTO requestDTO);
    RecursoResponseDTO findRecursoById(Long id);
    List<RecursoResponseDTO> listRecursos();

}
