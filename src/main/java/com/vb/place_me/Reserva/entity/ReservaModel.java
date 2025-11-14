package com.vb.place_me.Reserva.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vb.place_me.Pagamento.entity.PagamentoModel;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;
import com.vb.place_me.Reserva.enums.StatusReserva;
import com.vb.place_me.Usuario.entity.UsuarioModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReservaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @CreatedDate
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;
    @LastModifiedDate
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    @JsonIgnore
    private PropriedadeModel propriedade;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private UsuarioModel usuario;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private PagamentoModel pagamento;

}
