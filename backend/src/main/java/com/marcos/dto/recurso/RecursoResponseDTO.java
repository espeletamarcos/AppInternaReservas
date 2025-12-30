package com.marcos.dto.recurso;

import com.marcos.model.enums.TipoRecurso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecursoResponseDTO {

    private Long id;
    private String nombre;
    private TipoRecurso tipoRecurso;
    private boolean activo;
}
