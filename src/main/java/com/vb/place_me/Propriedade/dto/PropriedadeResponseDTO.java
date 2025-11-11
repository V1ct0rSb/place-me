package com.vb.place_me.Propriedade.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vb.place_me.Propriedade.enums.TipoPropriedade;

public record PropriedadeResponseDTO(
    Long id,
    String titulo,
    String descricao,
    TipoPropriedade tipo,
    BigDecimal precoPorNoite,
    Boolean ativo,
    String cep,
    String logradouro,
    String complemento,
    String bairro,
    String uf,
    String estado,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
}
