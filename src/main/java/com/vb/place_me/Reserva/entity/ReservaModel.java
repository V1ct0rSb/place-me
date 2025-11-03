package com.vb.place_me.Reserva.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.vb.place_me.Reserva.enums.StatusReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_reservas")
public class ReservaModel {
    private Long id;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusReserva status;
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
