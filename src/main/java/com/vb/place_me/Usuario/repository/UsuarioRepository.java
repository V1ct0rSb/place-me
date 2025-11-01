package com.vb.place_me.Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.place_me.Usuario.entity.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
