package com.br.thayane.repository;

import com.br.thayane.entity.RedeSocial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedeSocialRepository extends JpaRepository<RedeSocial, Long> {
    List<RedeSocial> findByAtivoTrueOrderByIdAsc();
}
