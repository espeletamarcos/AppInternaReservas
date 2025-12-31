package com.marcos.dto.recurso;

import com.marcos.model.enums.TipoRecurso;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecursoResponseDTO {

    private Long id;
    private String nombre;
    private TipoRecurso tipoRecurso;
    private boolean activo;
}
