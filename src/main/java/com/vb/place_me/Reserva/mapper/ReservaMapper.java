package com.vb.place_me.Reserva.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.place_me.Reserva.dto.ReservaCreateDTO;
import com.vb.place_me.Reserva.dto.ReservaResponseDTO;
import com.vb.place_me.Reserva.entity.ReservaModel;

@Mapper(componentModel = "spring")
public interface ReservaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "propriedade", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "pagamento", ignore = true)
    ReservaModel toEntity(ReservaCreateDTO dto);

    ReservaResponseDTO toResponse(ReservaModel model);

}
