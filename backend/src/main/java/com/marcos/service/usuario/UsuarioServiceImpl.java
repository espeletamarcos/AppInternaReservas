package com.marcos.service.usuario;

import com.marcos.dto.usuario.UsuarioRequestDTO;
import com.marcos.dto.usuario.UsuarioResponseDTO;
import com.marcos.exceptions.usuario.EmailAlreadyExistsException;
import com.marcos.exceptions.usuario.UsuarioNotFoundException;
import com.marcos.model.entity.Usuario;
import com.marcos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    @Override
    public UsuarioResponseDTO createUsuario(UsuarioRequestDTO requestDTO) {
        String emailRequest = requestDTO.getEmail();
        if(usuarioRepository.existsByEmail(emailRequest)) {
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }
        //Creamos la entidad a partir de los datos de la request
        Usuario usuario = new Usuario();
        usuario.setNombre(requestDTO.getNombre());
        usuario.setEmail(requestDTO.getEmail().toLowerCase());
        usuario.setActivo(true);
        //Guardamos en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        //Construimos el UsuarioResponseDTO
        return UsuarioResponseDTO.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .email(usuarioGuardado.getEmail())
                .activo(usuarioGuardado.isActivo())
                .build();
    }

    @Transactional
    @Override
    public UsuarioResponseDTO findUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .activo(usuario.isActivo())
                .build();
    }

    @Override
    public List<UsuarioResponseDTO> listUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        return listaUsuarios.stream() // El .stream es básicamente un bucle en una lista
                .filter(Usuario::isActivo) // Sólo usuarios activos
                .map(usuario -> UsuarioResponseDTO.builder() // Transforma cada elemento del map
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .email(usuario.getEmail())
                        .activo(usuario.isActivo())
                        .build())
                .toList();
    }
}
