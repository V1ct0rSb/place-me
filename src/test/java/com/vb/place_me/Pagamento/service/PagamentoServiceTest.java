package com.vb.place_me.Pagamento.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @Mock
    private PagamentoMapper pagamentoMapper;

    @InjectMocks
    private PagamentoService pagamentoService;

    private ReservaModel reserva;
    private PropriedadeModel propriedade;
    private PagamentoModel pagamento;
    private PagamentoResponseDTO pagamentoResponse;

    @BeforeEach
    void setUp() {
        propriedade = new PropriedadeModel();
        propriedade.setId(1L);
        propriedade.setAtivo(true);

        reserva = new ReservaModel();
        reserva.setId(1L);
        reserva.setValorTotal(BigDecimal.valueOf(500.00));
        reserva.setStatus(StatusReserva.PENDENTE);
        reserva.setPropriedade(propriedade);

        pagamento = new PagamentoModel();
        pagamento.setId(1L);
        pagamento.setReserva(reserva);
        pagamento.setValor(BigDecimal.valueOf(500.00));
        pagamento.setStatus(StatusPagamento.PENDENTE);

        pagamentoResponse = new PagamentoResponseDTO(1L, BigDecimal.valueOf(500.00), StatusPagamento.PENDENTE);
    }

    // ========== TESTES CRIAR PAGAMENTO ==========

    @Test
    @DisplayName("Deve criar pagamento com sucesso")
    void deveCriarPagamentoComSucesso() {
        //GIVEN
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(pagamentoRepository.existsByReservaIdAndStatus(1L, StatusPagamento.PENDENTE)).thenReturn(false);
        when(pagamentoRepository.save(any(PagamentoModel.class))).thenReturn(pagamento);
        when(pagamentoMapper.toResponse(pagamento)).thenReturn(pagamentoResponse);

        //WHEN
        PagamentoResponseDTO resultado = pagamentoService.criarPagamento(1L);

        //THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals(BigDecimal.valueOf(500.00), resultado.valor());
        assertEquals(StatusPagamento.PENDENTE, resultado.status());

        verify(reservaRepository, times(1)).findById(1L);
        verify(pagamentoRepository, times(1)).existsByReservaIdAndStatus(1L, StatusPagamento.PENDENTE);
        verify(pagamentoRepository, times(1)).save(any(PagamentoModel.class));
        verify(pagamentoMapper, times(1)).toResponse(pagamento);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar pagamento quando reserva não existe")
    void deveLancarExcecaoQuandoReservaNaoExiste() {
        // GIVEN
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.criarPagamento(1L));

        //THEN
        assertEquals("Id da reserva não encontrada!!", exception.getMessage());
        verify(reservaRepository, times(1)).findById(1L);
        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando já existe pagamento pendente")
    void deveLancarExcecaoQuandoExistePagamentoPendente() {
        //GIVEN
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(pagamentoRepository.existsByReservaIdAndStatus(1L, StatusPagamento.PENDENTE)).thenReturn(true);

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.criarPagamento(1L));

        //THEN
        assertEquals("Existe pagamento em aberto para reserva solicitada!", exception.getMessage());
        verify(pagamentoRepository, never()).save(any());
    }

    // ========== TESTES REALIZAR PAGAMENTO ==========

    @Test
    @DisplayName("Deve realizar pagamento com sucesso")
    void deveRealizarPagamentoComSucesso() {
        //GIVEN
        PagamentoResponseDTO pagamentoPagoResponse =
            new PagamentoResponseDTO(1L, BigDecimal.valueOf(500.00), StatusPagamento.PAGO);

        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(pagamentoMapper.toResponse(pagamento)).thenReturn(pagamentoPagoResponse);

        //WHEN
        PagamentoResponseDTO resultado = pagamentoService.realizarPagamento(1L);

        //THEN
        assertNotNull(resultado);
        assertEquals(StatusPagamento.PAGO, resultado.status());
        assertEquals(StatusPagamento.PAGO, pagamento.getStatus());
        assertEquals(StatusReserva.ACEITO, reserva.getStatus());
        assertFalse(propriedade.getAtivo());

        verify(pagamentoRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).findById(1L);
        verify(propriedadeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao realizar pagamento quando pagamento não existe")
    void deveLancarExcecaoQuandoPagamentoNaoExiste() {
        //GIVEN
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.realizarPagamento(1L));

        //THEN
        assertEquals("Id do pagamento não encontrado", exception.getMessage());
        verify(pagamentoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao realizar pagamento recusado")
    void deveLancarExcecaoQuandoPagamentoRecusado() {
        //GIVEN
        pagamento.setStatus(StatusPagamento.RECUSADO);
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.realizarPagamento(1L));

        //THEN
        assertEquals("Pagamento RECUSADO, efetue uma nova compra!!", exception.getMessage());
        verify(pagamentoRepository, times(1)).findById(1L);
        verify(reservaRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando reserva não existe ao realizar pagamento")
    void deveLancarExcecaoQuandoReservaNaoExisteAoRealizarPagamento() {
        //GIVEN
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.realizarPagamento(1L));

        //THEN
        assertEquals("Id da reserva não encontrada!!", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando propriedade não existe ao realizar pagamento")
    void deveLancarExcecaoQuandoPropriedadeNaoExisteAoRealizarPagamento() {
        //GIVEN
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.realizarPagamento(1L));

        //THEN
        assertEquals("Id da propriedade não encontrada!!", exception.getMessage());
    }

    // ========== TESTES DESISTIR PAGAMENTO ==========

    @Test
    @DisplayName("Deve desistir do pagamento com sucesso")
    void deveDesistirDoPagamentoComSucesso() {
        //GIVEN
        PagamentoResponseDTO pagamentoRecusadoResponse =
            new PagamentoResponseDTO(1L, BigDecimal.valueOf(500.00), StatusPagamento.RECUSADO);

        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(pagamentoMapper.toResponse(pagamento)).thenReturn(pagamentoRecusadoResponse);

        //WHEN
        PagamentoResponseDTO resultado = pagamentoService.desistirPagamento(1L);

        //THEN
        assertNotNull(resultado);
        assertEquals(StatusPagamento.RECUSADO, resultado.status());
        assertEquals(StatusPagamento.RECUSADO, pagamento.getStatus());
        assertEquals(StatusReserva.CANCELADO, reserva.getStatus());
        assertTrue(propriedade.getAtivo());

        verify(pagamentoRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao desistir quando pagamento não existe")
    void deveLancarExcecaoQuandoPagamentoNaoExisteAoDesistir() {
        //GIVEN
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.desistirPagamento(1L));

        //THEN
        assertEquals("Id do pagamento não encontrado", exception.getMessage());
        verify(pagamentoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao desistir de pagamento já efetuado")
    void deveLancarExcecaoQuandoPagamentoJaEfetuado() {
        //GIVEN
        pagamento.setStatus(StatusPagamento.PAGO);
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.desistirPagamento(1L));

        //THEN
        assertEquals("Pagamento já efetuado! Entre em contato com o Proprietario!!", exception.getMessage());
        verify(pagamentoMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando reserva não existe ao desistir")
    void deveLancarExcecaoQuandoReservaNaoExisteAoDesistir() {
        //GIVEN
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.desistirPagamento(1L));

        //THEN
        assertEquals("Id da reserva não encontrada!!", exception.getMessage());
    }
}