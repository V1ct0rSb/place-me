package com.vb.place_me.Usuario.entity;

import java.util.ArrayList;
import java.util.List;

import com.vb.place_me.Propriedade.entity.PropriedadeModel;
import com.vb.place_me.Reserva.entity.ReservaModel;
import com.vb.place_me.Usuario.enums.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @OneToMany(mappedBy = "usuario")
    private List<PropriedadeModel> propriedades = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<ReservaModel> reservas = new ArrayList<>();
}
