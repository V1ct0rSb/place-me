package com.vb.place_me.Propriedade.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.vb.place_me.Propriedade.dto.PropriedadeCreateDTO;
import com.vb.place_me.Propriedade.dto.PropriedadeResponseDTO;
import com.vb.place_me.Propriedade.dto.ViaCepDTO;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;
import com.vb.place_me.Propriedade.mapper.PropriedadeMapper;
import com.vb.place_me.Propriedade.repository.PropriedadeRepository;

import static com.vb.place_me.Propriedade.enums.TipoPropriedade.CHACARA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropriedadeServiceTest {
    @InjectMocks
    private PropriedadeService service;

    @Mock
    private PropriedadeRepository repository;

    @Mock
    private PropriedadeMapper mapper;

    @Mock
    private ViaCepService viaCepService;

    private PropriedadeCreateDTO propriedadeCreateDTO;
    private PropriedadeResponseDTO propriedadeResponseDTO;
    private PropriedadeModel propriedade;
    private ViaCepDTO viaCepDTO;

    @BeforeEach
    void setUp() {
        propriedadeCreateDTO = new PropriedadeCreateDTO("Chacará Boa Luz",
                                                        "Chacará com piscina",
                                                        CHACARA,
                                                        BigDecimal.valueOf(100.00),
                                                        "49000-173");

        // CRIAR O MOCK DO ViaCepDTO
        viaCepDTO = new ViaCepDTO("49000173",
                                  "Rua Antônio Oliveira Freire Piuga",
                                  null,
                                  null,
                                  "Aruana",
                                  "Aracaju",
                                  "SE",
                                  "Sergipe",
                                  "Nordeste",
                                  "2800308",
                                  null,
                                  "79",
                                  "3105");

        propriedade = new PropriedadeModel();
        propriedade.setId(1L);
        propriedade.setTitulo("Chacará Boa Luz");
        propriedade.setDescricao("Chacará com piscina");
        propriedade.setTipo(CHACARA);
        propriedade.setPrecoPorNoite(BigDecimal.valueOf(100.00));
        propriedade.setAtivo(true);
        propriedade.setCep("49000173");
        propriedade.setLogradouro("Rua Antônio Oliveira Freire Piuga");
        propriedade.setComplemento("");
        propriedade.setBairro("Aruana");
        propriedade.setUf("SE");
        propriedade.setEstado("Sergipe");
        propriedade.setCriadoEm(LocalDateTime.parse("2025-11-18T10:55:45.576"));
        propriedade.setAtualizadoEm(LocalDateTime.parse("2025-11-18T10:55:45.576"));

        propriedadeResponseDTO = new PropriedadeResponseDTO(1L,
                                                            "Chacará Boa Luz",
                                                            "Chacará com piscina",
                                                            CHACARA,
                                                            BigDecimal.valueOf(100.00),
                                                            true,
                                                            "49000173",
                                                            "Rua Antônio Oliveira Freire Piuga",
                                                            "",
                                                            "Aruana",
                                                            "SE",
                                                            "Sergipe",
                                                            LocalDateTime.parse("2025-11-18T10:55:45.576"),
                                                            LocalDateTime.parse("2025-11-18T10:55:45.576"));
    }

    @Test
    void criarPropriedade() {
        //GIVEN
        when(viaCepService.buscarDadosPorCep(anyString())).thenReturn(viaCepDTO);
        when(mapper.toEntity(propriedadeCreateDTO)).thenReturn(propriedade);
        when(repository.save(any(PropriedadeModel.class))).thenReturn(propriedade);
        when(mapper.toResponse(propriedade)).thenReturn(propriedadeResponseDTO);

        //WHEN
        PropriedadeResponseDTO result = service.criarPropriedade(propriedadeCreateDTO);

        //THEN
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Chacará Boa Luz", result.titulo());
        assertEquals("Chacará com piscina", result.descricao());
        assertEquals(CHACARA, result.tipo());
        assertEquals(BigDecimal.valueOf(100.00), result.precoPorNoite());
        assertEquals(true, result.ativo());
        assertEquals("49000173", result.cep());
        assertEquals("Rua Antônio Oliveira Freire Piuga", result.logradouro());
        assertEquals("Aruana", result.bairro());
        assertEquals("SE", result.uf());
        assertEquals("Sergipe", result.estado());

        verify(viaCepService).buscarDadosPorCep(anyString());
        verify(mapper).toEntity(propriedadeCreateDTO);
        verify(repository).save(any(PropriedadeModel.class));
        verify(mapper).toResponse(propriedade);
    }

    @Test
    void listarPropriedades() {
        //GIVEN
        List<PropriedadeModel> propriedades = Collections.singletonList(propriedade);
        List<PropriedadeResponseDTO> propriedadesResponse = Collections.singletonList(propriedadeResponseDTO);

        when(repository.findAll()).thenReturn(propriedades);
        when(mapper.toDoList(propriedades)).thenReturn(propriedadesResponse);

        //WHEN
        List<PropriedadeResponseDTO> result = service.listarPropriedades();

        //THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Chacará Boa Luz",
                     result.get(0)
                         .titulo());

        verify(repository).findAll();
        verify(mapper).toDoList(propriedades);
    }

    @Test
    void buscarPropriedadePorId() {
        //GIVEN
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(propriedade));
        when(mapper.toResponse(propriedade)).thenReturn(propriedadeResponseDTO);

        //WHEN
        PropriedadeResponseDTO result = service.buscarPropriedadePorId(id);

        //THEN
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Chacará Boa Luz", result.titulo());
        assertEquals("Chacará com piscina", result.descricao());

        verify(repository).findById(id);
        verify(mapper).toResponse(propriedade);
    }

    @Test
    void editarPropriedade() {
        //GIVEN
        Long id = 1L;
        PropriedadeCreateDTO updateDTO = new PropriedadeCreateDTO("Chacará Boa Luz",
                                                                  "Chacará com piscina e bem organizada",
                                                                  CHACARA,
                                                                  BigDecimal.valueOf(100.00),
                                                                  "49000-173");

        when(repository.findById(id)).thenReturn(Optional.of(propriedade));
        when(repository.save(any(PropriedadeModel.class))).thenReturn(propriedade);
        when(mapper.toResponse(propriedade)).thenReturn(propriedadeResponseDTO);

        //WHEN
        PropriedadeResponseDTO result = service.editarPropriedade(updateDTO, id);

        //THEN
        assertNotNull(result);
        verify(repository).findById(id);
        verify(repository).save(propriedade);
        verify(mapper).toResponse(propriedade);
    }

    @Test
    void deletarPropriedade() {
        // GIVEN
        Long id = 1L;
        doNothing().when(repository)
            .deleteById(id);

        // WHEN
        service.deletarPropriedade(id);

        // THEN
        verify(repository).deleteById(id);
    }
}