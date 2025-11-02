package com.vb.place_me.Propriedade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.place_me.Propriedade.entity.PropriedadeModel;

public interface PropriedadeRepository extends JpaRepository<PropriedadeModel, Long> {
}
