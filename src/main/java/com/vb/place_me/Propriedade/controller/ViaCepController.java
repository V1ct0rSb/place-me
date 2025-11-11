package com.vb.place_me.Propriedade.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vb.place_me.Propriedade.dto.ViaCepDTO;
import com.vb.place_me.Propriedade.service.ViaCepService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ViaCepController {
    private final ViaCepService service;

    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ViaCepDTO> buscarDadosPorCep(@PathVariable("cep") String cep){
        return ResponseEntity.ok(service.buscarDadosPorCep(cep));
    }
}