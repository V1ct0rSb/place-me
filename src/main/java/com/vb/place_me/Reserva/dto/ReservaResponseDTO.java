package com.vb.place_me.Reserva.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.vb.place_me.Reserva.enums.StatusReserva;

public record ReservaResponseDTO(
    Long id,
    LocalDate dataInicio,
    LocalDate dataFim,
    BigDecimal valorTotal,
    StatusReserva status,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
}
