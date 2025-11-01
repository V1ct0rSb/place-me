package com.vb.place_me.Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.place_me.Usuario.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
