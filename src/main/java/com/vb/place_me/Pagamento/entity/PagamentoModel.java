package com.vb.place_me.Pagamento.entity;

import java.math.BigDecimal;

import com.vb.place_me.Pagamento.enums.StatusPagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;
    @Column(name = "status", nullable = false)
    private StatusPagamento status;
}
