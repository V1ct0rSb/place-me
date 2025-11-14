package com.vb.place_me.Pagamento.mapper;

import org.mapstruct.Mapper;
import com.vb.place_me.Pagamento.dto.PagamentoResponseDTO;
import com.vb.place_me.Pagamento.entity.PagamentoModel;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {
    PagamentoResponseDTO toResponse(PagamentoModel model);
}
