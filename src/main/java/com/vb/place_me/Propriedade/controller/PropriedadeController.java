package com.vb.place_me.Propriedade.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.place_me.Propriedade.dto.PropriedadeCreateDTO;
import com.vb.place_me.Propriedade.dto.PropriedadeResponseDTO;
import com.vb.place_me.Propriedade.service.PropriedadeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class PropriedadeController {
    private final PropriedadeService service;

    @PostMapping("/propriedades")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR')")
    public ResponseEntity<PropriedadeResponseDTO> criarPropriedade(@Valid @RequestBody PropriedadeCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(this.service.criarPropriedade(dto));
    }

    @GetMapping("/propriedades")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR', 'HOSPEDE')")
    public ResponseEntity<List<PropriedadeResponseDTO>> listarPropriedades() {
        return ResponseEntity.ok()
            .body(this.service.listarPropriedades());
    }

    @GetMapping("/propriedades/{id}")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR', 'HOSPEDE')")
    public ResponseEntity<PropriedadeResponseDTO> exibirPropriedadePorId(@PathVariable Long id) {
        return ResponseEntity.ok()
            .body(this.service.exibirPropriedadePorId(id));
    }

    @PatchMapping("/propriedade/{id}")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR')")
    public ResponseEntity<PropriedadeResponseDTO> editarPropriedade(
        @Valid
        @RequestBody PropriedadeCreateDTO dto,
        @PathVariable Long id) {
        return ResponseEntity.ok()
            .body(this.service.editarPropriedade(dto, id));
    }

    @DeleteMapping("/propriedade/{id}")
    @PreAuthorize("hasAnyRole('PROPRIETARIO', 'ADMINISTRADOR')")
    public ResponseEntity<Void> deletarPropriedade(@PathVariable Long id) {
        this.service.deletarPropriedade(id);
        return ResponseEntity.noContent()
            .build();
    }
}
