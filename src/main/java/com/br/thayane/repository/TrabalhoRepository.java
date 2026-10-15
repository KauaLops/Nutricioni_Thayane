package com.br.thayane.repository;

import com.br.thayane.entity.Trabalho;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabalhoRepository extends JpaRepository<Trabalho, Long> {
    List<Trabalho> findByAtivoTrueOrderByDataCriacaoDesc();
}
