package com.vb.place_me.Pagamento.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.place_me.Pagamento.dto.PagamentoResponseDTO;
import com.vb.place_me.Pagamento.entity.PagamentoModel;
import com.vb.place_me.Pagamento.enums.StatusPagamento;
import com.vb.place_me.Pagamento.mapper.PagamentoMapper;
import com.vb.place_me.Pagamento.repository.PagamentoRepository;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;
import com.vb.place_me.Propriedade.repository.PropriedadeRepository;
import com.vb.place_me.Reserva.entity.ReservaModel;
import com.vb.place_me.Reserva.enums.StatusReserva;
import com.vb.place_me.Reserva.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PagamentoService {
    private final PagamentoRepository repository;
    private final ReservaRepository reservaRepository;
    private final PropriedadeRepository propriedadeRepository;
    private final PagamentoMapper mapper;

    @Transactional
    public PagamentoResponseDTO criarPagamento(Long reservaId) {
        ReservaModel reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Id da reserva não encontrada!!"));

        //Verifica se existe RESERVA pendente de PAGAMENTO
        Boolean pagamentoPendente = repository.existsByReservaIdAndStatus(reservaId, StatusPagamento.PENDENTE);
        Boolean pagamentoPago = repository.existsByReservaIdAndStatus(reservaId, StatusPagamento.PAGO);

        if (pagamentoPendente) {
            throw new RuntimeException("Existe pagamento em aberto para reserva solicitada!");
        }

        PagamentoModel pagamento = new PagamentoModel();
        pagamento.setReserva(reserva);
        pagamento.setValor(reserva.getValorTotal());
        pagamento.setStatus(StatusPagamento.PENDENTE);

        PagamentoModel pagamentoSalvo = repository.save(pagamento);

        return mapper.toResponse(pagamentoSalvo);
    }

    @Transactional
    public PagamentoResponseDTO realizarPagamento(Long pagamentoId) {
        PagamentoModel pagamento = repository.findById(pagamentoId)
            .orElseThrow(() -> new RuntimeException("Id do pagamento não encontrado"));

        if (pagamento.getStatus()
            .equals(StatusPagamento.RECUSADO)) {
            throw new RuntimeException("Pagamento RECUSADO, efetue uma nova compra!!");
        }

        //Atualizar status do PAGAMENTO para PAGO
        pagamento.setStatus(StatusPagamento.PAGO);

        ReservaModel reserva = reservaRepository.findById(pagamento.getReserva()
                                                              .getId())
            .orElseThrow(() -> new RuntimeException("Id da reserva não encontrada!!"));

        //Atualizar status da RESERVA para ACEITO
        reserva.setStatus(StatusReserva.ACEITO);

        Long propriedadeId = reserva.getPropriedade()
            .getId();

        PropriedadeModel propriedade = propriedadeRepository.findById(propriedadeId)
            .orElseThrow(() -> new RuntimeException("Id da propriedade não encontrada!!"));

        //Atualizar status da PROPRIEDADE para FALSO
        propriedade.setAtivo(false);

        return mapper.toResponse(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO desistirPagamento(Long pagamentoId) {
        PagamentoModel pagamento = repository.findById(pagamentoId)
            .orElseThrow(() -> new RuntimeException("Id do pagamento não encontrado"));

        ReservaModel reserva = reservaRepository.findById(pagamento.getReserva()
                                                              .getId())
            .orElseThrow(() -> new RuntimeException("Id da reserva não encontrada!!"));

        //Verifica se USUARIO pagou
        if (pagamento.getStatus() == StatusPagamento.PAGO) {
            throw new RuntimeException("Pagamento já efetuado! Entre em contato com o Proprietario!!");
        }

        //Atualizar status da PAGAMENTO para RECUSADO
        pagamento.setStatus(StatusPagamento.RECUSADO);

        //Atualizar status da RESERVA para CANCELADO
        reserva.setStatus(StatusReserva.CANCELADO);

        //Garanti status da PROPRIEDADE para ATIVO
        reserva.getPropriedade()
            .setAtivo(true);

        return mapper.toResponse(pagamento);
    }


}
