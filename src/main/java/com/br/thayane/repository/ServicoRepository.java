package com.br.thayane.repository;

import com.br.thayane.entity.Servico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByAtivoTrueOrderByIdAsc();
}
