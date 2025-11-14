package com.vb.place_me.Usuario.dto;

import jakarta.validation.constraints.Email;

public record LoginCreateDTO(
    @Email(message = "Email é obrigatorio")
    String email,
    String senha
) {
}
