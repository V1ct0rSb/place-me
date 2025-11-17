package com.vb.place_me.Pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.place_me.Pagamento.entity.PagamentoModel;
import com.vb.place_me.Pagamento.enums.StatusPagamento;

public interface PagamentoRepository extends JpaRepository<PagamentoModel, Long> {
    Boolean existsByReservaIdAndStatus(Long reservaId, StatusPagamento statusPagamento);
}
