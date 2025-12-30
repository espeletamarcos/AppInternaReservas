package com.marcos.service.usuario;

import com.marcos.dto.usuario.UsuarioRequestDTO;
import com.marcos.dto.usuario.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO createUsuario (UsuarioRequestDTO requestDTO);
    UsuarioResponseDTO findUsuarioById (Long id);
    List<UsuarioResponseDTO> listUsuarios ();

}
