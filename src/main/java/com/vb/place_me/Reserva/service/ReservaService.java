package com.vb.place_me.Reserva.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.place_me.Pagamento.dto.PagamentoResponseDTO;
import com.vb.place_me.Pagamento.entity.PagamentoModel;
import com.vb.place_me.Pagamento.repository.PagamentoRepository;
import com.vb.place_me.Pagamento.service.PagamentoService;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;
import com.vb.place_me.Propriedade.repository.PropriedadeRepository;
import com.vb.place_me.Reserva.dto.ReservaCreateDTO;
import com.vb.place_me.Reserva.dto.ReservaResponseDTO;
import com.vb.place_me.Reserva.entity.ReservaModel;
import com.vb.place_me.Reserva.enums.StatusReserva;
import com.vb.place_me.Reserva.mapper.ReservaMapper;
import com.vb.place_me.Reserva.repository.ReservaRepository;
import com.vb.place_me.Usuario.entity.UsuarioModel;
import com.vb.place_me.Usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final PagamentoService pagamentoService;
    private final PagamentoRepository pagamentoRepository;
    private final PropriedadeRepository propriedadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReservaMapper mapper;

    @Transactional
    public ReservaResponseDTO criarReserva(ReservaCreateDTO dto) {
        UsuarioModel usuario = usuarioRepository.findById(dto.usuarioId())
            .orElseThrow(() -> new RuntimeException("usuario não encontrado!!"));

        PropriedadeModel propriedade = propriedadeRepository.findById(dto.propriedadeId())
            .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        ReservaModel reserva = mapper.toEntity(dto);

        reserva.setUsuario(usuario);
        reserva.setPropriedade(propriedade);
        reserva.setStatus(StatusReserva.PENDENTE);

        long dias = ChronoUnit.DAYS.between(dto.dataInicio(), dto.dataFim());
        BigDecimal valorTotalReserva = propriedade.getPrecoPorNoite()
            .multiply(BigDecimal.valueOf(dias));

        reserva.setValorTotal(valorTotalReserva);

        ReservaModel reservaSalva = reservaRepository.save(reserva);

        PagamentoResponseDTO pagamentoDto = pagamentoService.criarPagamento(reserva.getId());

        PagamentoModel pagamento = pagamentoRepository.findById(pagamentoDto.id())
            .orElseThrow(() -> new RuntimeException("Erro ao criar pagamento"));

        reservaSalva.setPagamento(pagamento);

        reservaSalva = reservaRepository.save(reservaSalva);

        return mapper.toResponse(reservaSalva);
    }
}
