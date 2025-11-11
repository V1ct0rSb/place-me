package com.vb.place_me.Usuario.dto;

import com.vb.place_me.Usuario.enums.TipoUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateDTO(
    @NotBlank(message = "O nome é obrigatório!!")
    String nome,
    @NotBlank(message = "O email é obrigatório!!")
    @Email(message = "O formato do email é invalido!!")
    String email,
    @NotBlank(message = "A senha é obrigatória!")
    String senha,
    @NotNull(message = "O tipo do usuário é obrigatório!!")
    TipoUsuario tipo,
    @NotNull(message = "O status do usuário é obrigatório!!")
    Boolean status
) {
}
