package com.vb.place_me.Usuario.repository;

import java.net.http.HttpHeaders;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.place_me.Usuario.entity.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByEmail(String email);
}
