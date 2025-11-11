package com.vb.place_me.Propriedade.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.vb.place_me.Propriedade.dto.ViaCepDTO;

@FeignClient(name = "via-cep", url = "https://viacep.com.br/ws/")
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    ViaCepDTO buscarDadosPorCep(@PathVariable("cep") String cep);
}