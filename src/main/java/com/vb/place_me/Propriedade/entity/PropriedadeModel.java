package com.vb.place_me.Propriedade.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vb.place_me.Propriedade.enums.TipoPropriedade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_propiedades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropriedadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "descricao", nullable = false)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPropriedade tipo;
    @Column(name = "preco_por_noite", nullable = false)
    private BigDecimal precoPorNoite;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    // --- Campos de ViaCep ---
    @Column(name = "cep", nullable = false)
    private String cep;
    @Column(name = "logradouro", nullable = false)
    private String logradouro;
    @Column(name = "complemento", nullable = false)
    private String complemento;
    @Column(name = "bairro", nullable = false)
    private String bairro;
    @Column(name = "uf", nullable = false)
    private String uf;
    @Column(name = "estado", nullable = false)
    private String estado;
    @Column(name = "criado_em", nullable = false)

    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

}
