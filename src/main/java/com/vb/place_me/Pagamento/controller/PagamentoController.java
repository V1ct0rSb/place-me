package com.vb.place_me.Pagamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.place_me.Pagamento.dto.PagamentoResponseDTO;
import com.vb.place_me.Pagamento.service.PagamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class PagamentoController {
    private final PagamentoService service;

    @PutMapping("/pagamentos/{id}/confirmar")
    @PreAuthorize("hasAnyRole('HOSPEDE', 'ADMINISTRADOR')")
    public ResponseEntity<PagamentoResponseDTO> realizarPagamento(@PathVariable Long id) {
        return ResponseEntity.ok()
            .body(service.realizarPagamento(id));
    }

    @PutMapping("/pagamentos/{id}/cancelar")
    @PreAuthorize("hasAnyRole('HOSPEDE', 'ADMINISTRADOR')")
    public ResponseEntity<PagamentoResponseDTO> desistirPagamento(@PathVariable Long id) {
        return ResponseEntity.ok()
            .body(service.desistirPagamento(id));
    }
}
