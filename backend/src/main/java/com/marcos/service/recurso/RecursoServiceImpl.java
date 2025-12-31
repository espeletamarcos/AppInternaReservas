package com.marcos.service.recurso;

import com.marcos.dto.recurso.RecursoRequestDTO;
import com.marcos.dto.recurso.RecursoResponseDTO;
import com.marcos.exceptions.recurso.NombreRecursoAlreadyExistsException;
import com.marcos.model.entity.Recurso;
import com.marcos.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class RecursoServiceImpl implements RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    @Transactional
    @Override
    public RecursoResponseDTO createRecurso(RecursoRequestDTO requestDTO) {
        String nombreRequest = requestDTO.getNombre().toLowerCase();
        if(recursoRepository.existsByNombre(nombreRequest)) {
            throw new NombreRecursoAlreadyExistsException("El nombre para este recurso ya existe");
        }
        // Creamos la entidad a partir de los datos de la request
        Recurso recurso = new Recurso();
        recurso.setNombre(requestDTO.getNombre());
        recurso.setTipoRecurso(requestDTO.getTipoRecurso());
        recurso.setActivo(true);
        // Guardamos la entidad en la base de datos
        Recurso recursoGuardado = recursoRepository.save(recurso);
        // Construimos el RecursoResponseDTO
        RecursoResponseDTO recursoResponseDTO = RecursoResponseDTO.builder() // Optimizar con devolver solo el builder
                .id(recursoGuardado.getId())
                .nombre(recursoGuardado.getNombre())
                .tipoRecurso(recursoGuardado.getTipoRecurso())
                .activo(recursoGuardado.isActivo())
                .build();
        return recursoResponseDTO;
    }
}
