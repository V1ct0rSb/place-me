package com.vb.place_me.Usuario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vb.place_me.Usuario.dto.UsuarioCreateDTO;
import com.vb.place_me.Usuario.dto.UsuarioResponseDTO;
import com.vb.place_me.Usuario.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioResponseDTO usuarioResponseDTO = service.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(usuarioResponseDTO);
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR', 'HOSPEDE')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarioResponseDTO = service.listarUsuarios();
        return ResponseEntity.ok()
            .body(usuarioResponseDTO);
    }

    @PatchMapping("/usuarios/{id}")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR', 'HOSPEDE')")
    public ResponseEntity<UsuarioResponseDTO> editarUsuario(@Valid @RequestBody UsuarioCreateDTO dto, @PathVariable Long id) {
        UsuarioResponseDTO usuarioResponseDTO = service.editarUsuario(dto, id);
        return ResponseEntity.ok()
            .body(usuarioResponseDTO);
    }

    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR', 'HOSPEDE')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent()
            .build();
    }
}
