package com.vb.place_me.Usuario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.place_me.Usuario.dto.UsuarioCreateDTO;
import com.vb.place_me.Usuario.dto.UsuarioResponseDTO;
import com.vb.place_me.Usuario.entity.UsuarioModel;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "propriedades", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    UsuarioModel toEntity(UsuarioCreateDTO dto);

    UsuarioResponseDTO toResponse(UsuarioModel model);

    List<UsuarioResponseDTO> toDtoList(List<UsuarioModel> usuarios);
}
