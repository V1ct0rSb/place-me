package com.vb.place_me.Usuario.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.place_me.Usuario.dto.UsuarioCreateDTO;
import com.vb.place_me.Usuario.dto.UsuarioResponseDTO;
import com.vb.place_me.Usuario.entity.UsuarioModel;
import com.vb.place_me.Usuario.mapper.UsuarioMapper;
import com.vb.place_me.Usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    //POST
    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {
        var usuario = mapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        var usuarioSalvo = repository.save(usuario);

        return mapper.toResponse(usuarioSalvo);
    }

    //GET
    public List<UsuarioResponseDTO> listarUsuarios() {
        List<UsuarioModel> usuarios = repository.findAll();
        return mapper.toDtoList(usuarios);
    }

    //PATH{/id)
    @Transactional
    public UsuarioResponseDTO editarUsuario(UsuarioCreateDTO dto, Long id) {
        UsuarioModel usuario = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Id: " + id + " não foi encontrado!!"));

        if (dto.nome() != null) {
            usuario.setNome(dto.nome());
        }
        if (dto.email() != null) {
            usuario.setEmail(dto.email());
        }
        if (dto.senha() != null) {
            usuario.setSenha(dto.senha());
        }
        if (dto.tipo() != null) {
            usuario.setSenha(dto.senha());
        }

        var usuarioSalvo = repository.save(usuario);

        return mapper.toResponse(usuarioSalvo);
    }

    //DELETE
    @Transactional
    public void deletarUsuario(Long id) {
        repository.deleteById(id);
    }
}
