package com.vb.place_me.Pagamento.dto;

import java.math.BigDecimal;

import com.vb.place_me.Pagamento.enums.StatusPagamento;

public record PagamentoResponseDTO(
    Long id, BigDecimal valor, StatusPagamento status
) {
}
