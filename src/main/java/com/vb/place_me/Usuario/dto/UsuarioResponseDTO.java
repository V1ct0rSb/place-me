package com.vb.place_me.Usuario.dto;

import com.vb.place_me.Usuario.enums.TipoUsuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    String senha,
    TipoUsuario tipo,
    Boolean status
) {
}
