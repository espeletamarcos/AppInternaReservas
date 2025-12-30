package com.marcos.dto.usuario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private boolean activo;

}
