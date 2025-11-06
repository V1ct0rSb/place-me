package com.vb.place_me.Propriedade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.place_me.Propriedade.dto.PropriedadeCreateDTO;
import com.vb.place_me.Propriedade.dto.PropriedadeResponseDTO;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;
import com.vb.place_me.Propriedade.mapper.PropriedadeMapper;
import com.vb.place_me.Propriedade.repository.PropriedadeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropriedadeService {

    private final PropriedadeRepository repository;
    private final PropriedadeMapper mapper;

    //POST
    @Transactional
    public PropriedadeResponseDTO criarPropriedade(PropriedadeCreateDTO dto) {

        PropriedadeModel propriedade = mapper.toEntity(dto);
        var propriedadeSalvo = this.repository.save(propriedade);
        return this.mapper.toResponse(propriedadeSalvo);
    }

    //GET
    public List<PropriedadeResponseDTO> listarPropriedades() {
        List<PropriedadeModel> propriedades = this.repository.findAll();
        return this.mapper.toDoList(propriedades);
    }


    //GET/{ID}
    @Transactional
    public PropriedadeResponseDTO exibirPropriedadePorId(Long id) {
        PropriedadeModel propriedade = this.repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Propriedade de id: " + id + " não encontrada!!"));
        return this.mapper.toResponse(propriedade);
    }

    //PATCH/{ID}
    @Transactional
    public PropriedadeResponseDTO editarPropriedade(PropriedadeCreateDTO dto, Long id) {
        PropriedadeModel propriedade = this.repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Propriedade de id: " + id + " não encontrada!!"));
        if (dto.titulo() != null) {
            propriedade.setTitulo(dto.titulo());
        }
        if (dto.titulo() != null) {
            propriedade.setDescricao(dto.descricao());
        }
        if (dto.tipo() != null) {
            propriedade.setTipo(dto.tipo());
        }
        if (dto.precoPorNoite() != null) {
            propriedade.setPrecoPorNoite(dto.precoPorNoite());
        }
        if (dto.ativo() != null) {
            propriedade.setAtivo(dto.ativo());
        }
        if (dto.cep() != null) {
            propriedade.setCep(dto.cep());
        }

        var propriedadeSalva = this.repository.save(propriedade);

        return this.mapper.toResponse(propriedadeSalva);
    }

    //DELETE/{ID}
    @Transactional
    public void deletarPropriedade(Long id) {
        this.repository.deleteById(id);
    }

}
