package com.vb.place_me.Propriedade.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.place_me.Propriedade.dto.PropriedadeCreateDTO;
import com.vb.place_me.Propriedade.dto.PropriedadeResponseDTO;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;

@Mapper(componentModel = "spring")
public interface PropriedadeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "logradouro", ignore = true)
    @Mapping(target = "complemento", ignore = true)
    @Mapping(target = "bairro", ignore = true)
    @Mapping(target = "uf", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    PropriedadeModel toEntity(PropriedadeCreateDTO dto);

    PropriedadeResponseDTO toResponse(PropriedadeModel model);

    List<PropriedadeResponseDTO> toDoList(List<PropriedadeModel> propriedades);
}
