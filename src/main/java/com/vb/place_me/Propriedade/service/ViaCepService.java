package com.vb.place_me.Propriedade.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import com.vb.place_me.Propriedade.client.ViaCepClient;
import com.vb.place_me.Propriedade.dto.ViaCepDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    private final ViaCepClient client;

    public ViaCepDTO buscarDadosPorCep(String cep) {
        return client.buscarDadosPorCep(verificadorCep(cep));
    }

    public String verificadorCep(String cep) {
        String cepFormatado = cep.replace(" ", "").replace("-", "");

        if (!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)) {
            throw new RuntimeException("Dado invalidos");
        }

        return cepFormatado;
    }

}

