package com.vb.place_me.Reserva.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.place_me.Reserva.dto.ReservaCreateDTO;
import com.vb.place_me.Reserva.dto.ReservaResponseDTO;
import com.vb.place_me.Reserva.service.ReservaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService service;

    @PostMapping("/reservas")
    public ResponseEntity<ReservaResponseDTO> criarReserva(@Valid @RequestBody ReservaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.criarReserva(dto));
    }
}
