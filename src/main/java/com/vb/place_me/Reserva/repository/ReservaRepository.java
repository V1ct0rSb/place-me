package com.vb.place_me.Reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.place_me.Reserva.entity.ReservaModel;

public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {
}
