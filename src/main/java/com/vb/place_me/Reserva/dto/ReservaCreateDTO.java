package com.vb.place_me.Reserva.dto;

import java.time.LocalDate;

public record ReservaCreateDTO(
    LocalDate dataInicio,
    LocalDate dataFim,
    Long propriedadeId,
    Long usuarioId
) {
}
