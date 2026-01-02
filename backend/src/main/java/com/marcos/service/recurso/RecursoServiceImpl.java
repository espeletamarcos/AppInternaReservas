package com.marcos.service.recurso;

import com.marcos.dto.recurso.RecursoRequestDTO;
import com.marcos.dto.recurso.RecursoResponseDTO;
import com.marcos.dto.usuario.UsuarioResponseDTO;
import com.marcos.exceptions.recurso.NombreRecursoAlreadyExistsException;
import com.marcos.exceptions.recurso.RecursoNotFoundException;
import com.marcos.model.entity.Recurso;
import com.marcos.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        return RecursoResponseDTO.builder() // Optimizar con devolver solo el builder
                .id(recursoGuardado.getId())
                .nombre(recursoGuardado.getNombre())
                .tipoRecurso(recursoGuardado.getTipoRecurso())
                .activo(recursoGuardado.isActivo())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public RecursoResponseDTO findRecursoById(Long id) {
        // Buscamos el Recurso y si no lo encuentra, lanza una excepción
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("El recurso no se ha encontrado"));
        // Si lo encuentra, construimos RecursoResponseDTO
        return RecursoResponseDTO.builder()
                .id(recurso.getId())
                .nombre(recurso.getNombre())
                .tipoRecurso(recurso.getTipoRecurso())
                .activo(recurso.isActivo())
                .build();
    }

    @Override
    public List<RecursoResponseDTO> listRecursos() {
        // Recibimos todos los recursos que encuentre el repositorio
        List<Recurso> listaRecursos = recursoRepository.findAll();
        // Los mapeamos a DTO
        return listaRecursos.stream()
                .filter(Recurso::isActivo)
                .map(recurso -> RecursoResponseDTO.builder() // Transforma cada elemento del map
                        .id(recurso.getId())
                        .nombre(recurso.getNombre())
                        .tipoRecurso(recurso.getTipoRecurso())
                        .activo(recurso.isActivo())
                        .build())
                .toList();
    }
}
