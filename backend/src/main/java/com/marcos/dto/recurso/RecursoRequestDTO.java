package com.marcos.dto.recurso;

import com.marcos.model.enums.TipoRecurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecursoRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String nombre;

    @NotNull
    private TipoRecurso tipoRecurso;
}
