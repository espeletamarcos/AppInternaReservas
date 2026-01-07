package com.marcos.controller;

import com.marcos.dto.usuario.UsuarioRequestDTO;
import com.marcos.dto.usuario.UsuarioResponseDTO;
import com.marcos.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO create(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.createUsuario(usuarioRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDTO> listUsuarios() {
        return usuarioService.listUsuarios();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO findUsuarioById(@PathVariable Long id) {
        return usuarioService.findUsuarioById(id);
    }
}
