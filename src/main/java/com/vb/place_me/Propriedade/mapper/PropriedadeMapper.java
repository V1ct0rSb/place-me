package com.vb.place_me.Propriedade.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.place_me.Propriedade.dto.PropriedadeCreateDTO;
import com.vb.place_me.Propriedade.dto.PropriedadeResponseDTO;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;

@Mapper(componentModel = "spring")
public interface PropriedadeMapper {
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    PropriedadeModel toEntity(PropriedadeCreateDTO dto);

    PropriedadeResponseDTO toResponse(PropriedadeModel model);

    List<PropriedadeResponseDTO> toDoList(List<PropriedadeModel> propriedades);
}
