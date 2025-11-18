package com.vb.place_me.Usuario.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vb.place_me.Usuario.dto.UsuarioCreateDTO;
import com.vb.place_me.Usuario.dto.UsuarioResponseDTO;
import com.vb.place_me.Usuario.entity.UsuarioModel;
import com.vb.place_me.Usuario.mapper.UsuarioMapper;
import com.vb.place_me.Usuario.repository.UsuarioRepository;

import static com.vb.place_me.Usuario.enums.TipoUsuario.HOSPEDE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UsuarioCreateDTO usuarioCreateDTO;
    private UsuarioResponseDTO usuarioResponseDTO;
    private UsuarioModel usuarioModel;

    @BeforeEach
    void setUp() {
        usuarioCreateDTO = new UsuarioCreateDTO("Victor Barreto", "victor@gmail.com", "123456", HOSPEDE);

        usuarioModel = new UsuarioModel();
        usuarioModel.setId(1L);
        usuarioModel.setNome("Victor Barreto");
        usuarioModel.setEmail("victor@gmail.com");
        usuarioModel.setSenha("senhaEncriptada");
        usuarioModel.setTipo(HOSPEDE);
        usuarioModel.setStatus(true);

        usuarioResponseDTO =
            new UsuarioResponseDTO(1L, "Victor Barreto", "victor@gmail.com", "senhaEncriptada", HOSPEDE, true);
    }

    @Test
    void criarUsuario() {
        // GIVEN
        when(mapper.toEntity(usuarioCreateDTO)).thenReturn(usuarioModel);
        when(passwordEncoder.encode("123456")).thenReturn("senhaEncriptada");
        when(repository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);
        when(mapper.toResponse(usuarioModel)).thenReturn(usuarioResponseDTO);

        // WHEN
        UsuarioResponseDTO result = service.criarUsuario(usuarioCreateDTO);

        // THEN
        assertNotNull(result);
        assertEquals("Victor Barreto", result.nome());
        assertEquals("victor@gmail.com", result.email());
        assertEquals("senhaEncriptada", result.senha());
        assertEquals(HOSPEDE, result.tipo());
        assertEquals(true, result.status());

        verify(mapper).toEntity(usuarioCreateDTO);
        verify(passwordEncoder).encode("123456");
        verify(repository).save(any(UsuarioModel.class));
        verify(mapper).toResponse(usuarioModel);
    }

    @Test
    void listarUsuarios() {
        // GIVEN
        List<UsuarioModel> usuarios = Collections.singletonList(usuarioModel);
        List<UsuarioResponseDTO> usuariosResponse = Collections.singletonList(usuarioResponseDTO);

        when(repository.findAll()).thenReturn(usuarios);
        when(mapper.toDtoList(usuarios)).thenReturn(usuariosResponse);

        // WHEN
        List<UsuarioResponseDTO> result = service.listarUsuarios();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Victor Barreto",
                     result.get(0)
                         .nome());

        verify(repository).findAll();
        verify(mapper).toDtoList(usuarios);
    }

    @Test
    void editarUsuario() {
        // GIVEN
        Long id = 1L;
        UsuarioCreateDTO updateDTO =
            new UsuarioCreateDTO("Victor Atualizado", "victor.novo@gmail.com", "novaSenha", HOSPEDE);

        when(repository.findById(id)).thenReturn(Optional.of(usuarioModel));
        when(repository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);
        when(mapper.toResponse(usuarioModel)).thenReturn(usuarioResponseDTO);

        // WHEN
        UsuarioResponseDTO result = service.editarUsuario(updateDTO, id);

        // THEN
        assertNotNull(result);
        verify(repository).findById(id);
        verify(repository).save(usuarioModel);
        verify(mapper).toResponse(usuarioModel);
    }

    @Test
    void editarUsuario_DeveLancarExcecao_QuandoIdNaoExistir() {
        // GIVEN
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        RuntimeException exception =
            assertThrows(RuntimeException.class, () -> service.editarUsuario(usuarioCreateDTO, id));

        assertEquals("Id: 999 não foi encontrado!!", exception.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deletarUsuario() {
        // GIVEN
        Long id = 1L;
        doNothing().when(repository)
            .deleteById(id);

        // WHEN
        service.deletarUsuario(id);

        // THEN
        verify(repository).deleteById(id);
    }
}