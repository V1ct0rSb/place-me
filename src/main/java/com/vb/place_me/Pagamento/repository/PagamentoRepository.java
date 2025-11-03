package com.vb.place_me.Pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.place_me.Pagamento.entity.PagamentoModel;

public interface PagamentoRepository extends JpaRepository<PagamentoModel, Long> {
}
