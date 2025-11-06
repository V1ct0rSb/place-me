package com.vb.place_me.Propriedade.dto;

import java.math.BigDecimal;

import com.vb.place_me.Propriedade.enums.TipoPropriedade;

public record PropriedadeCreateDTO(
    String titulo,
    String descricao,
    TipoPropriedade tipo,
    BigDecimal precoPorNoite,
    Boolean ativo,
    String cep
) {

}
