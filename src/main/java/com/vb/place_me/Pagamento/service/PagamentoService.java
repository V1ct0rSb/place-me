package com.vb.place_me.Pagamento.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.place_me.Pagamento.dto.PagamentoResponseDTO;
import com.vb.place_me.Pagamento.entity.PagamentoModel;
import com.vb.place_me.Pagamento.enums.StatusPagamento;
import com.vb.place_me.Pagamento.mapper.PagamentoMapper;
import com.vb.place_me.Pagamento.repository.PagamentoRepository;
import com.vb.place_me.Reserva.entity.ReservaModel;
import com.vb.place_me.Reserva.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PagamentoService {
    private final PagamentoRepository repository;
    private final ReservaRepository reservaRepository;
    private final PagamentoMapper mapper;

    @Transactional
    public PagamentoResponseDTO criarPagamento(Long reservaId) {
        ReservaModel reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Id da reserva não encontrada!!"));

        PagamentoModel pagamento = new PagamentoModel();
        pagamento.setReserva(reserva);
        pagamento.setValor(reserva.getValorTotal());
        pagamento.setStatus(StatusPagamento.PENDENTE);

        PagamentoModel pagamentoSalvo = repository.save(pagamento);

        return mapper.toResponse(pagamentoSalvo);

    }
}
