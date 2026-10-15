package com.br.thayane.service;

import com.br.thayane.dto.TrabalhoForm;
import com.br.thayane.entity.Trabalho;
import com.br.thayane.exception.RecursoNaoEncontradoException;
import com.br.thayane.repository.TrabalhoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class TrabalhoService {
    private final TrabalhoRepository repo;

    public List<Trabalho> listarAtivos() { return repo.findByAtivoTrueOrderByDataCriacaoDesc(); }
    public List<Trabalho> listarTodos() { return repo.findAll(Sort.by(Sort.Direction.DESC, "id")); }

    public TrabalhoForm form(Long id) {
        Trabalho t = buscar(id);
        TrabalhoForm f = new TrabalhoForm();
        f.setId(t.getId()); f.setTitulo(t.getTitulo()); f.setDescricao(t.getDescricao());
        f.setCategoria(t.getCategoria()); f.setImagemUrl(t.getImagemUrl()); f.setAtivo(t.isAtivo());
        return f;
    }

    @Transactional
    public void salvar(TrabalhoForm f) {
        Trabalho t = f.getId() == null ? new Trabalho() : buscar(f.getId());
        t.setTitulo(f.getTitulo().trim()); t.setDescricao(f.getDescricao()); t.setCategoria(f.getCategoria().trim());
        t.setImagemUrl(f.getImagemUrl() == null || f.getImagemUrl().isBlank() ? "/images/hero.jpg" : f.getImagemUrl().trim());
        t.setAtivo(f.isAtivo());
        repo.save(t);
    }

    @Transactional public void excluir(Long id) { repo.delete(buscar(id)); }

    private Trabalho buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Trabalho não encontrado"));
    }
}
