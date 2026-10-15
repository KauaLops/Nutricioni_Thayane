package com.br.thayane.service;

import com.br.thayane.dto.ServicoForm;
import com.br.thayane.entity.Servico;
import com.br.thayane.exception.RecursoNaoEncontradoException;
import com.br.thayane.repository.ServicoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ServicoService {
    private final ServicoRepository repo;

    public List<Servico> listarAtivos() { return repo.findByAtivoTrueOrderByIdAsc(); }
    public List<Servico> listarTodos() { return repo.findAll(Sort.by("id")); }

    public ServicoForm form(Long id) {
        Servico s = buscar(id);
        ServicoForm f = new ServicoForm();
        f.setId(s.getId()); f.setNome(s.getNome()); f.setDescricao(s.getDescricao());
        f.setPreco(s.getPreco()); f.setBeneficios(s.getBeneficios()); f.setAtivo(s.isAtivo());
        return f;
    }

    @Transactional
    public void salvar(ServicoForm f) {
        Servico s = f.getId() == null ? new Servico() : buscar(f.getId());
        s.setNome(f.getNome().trim()); s.setDescricao(f.getDescricao().trim());
        s.setPreco(f.getPreco()); s.setBeneficios(f.getBeneficios()); s.setAtivo(f.isAtivo());
        repo.save(s);
    }

    @Transactional public void excluir(Long id) { repo.delete(buscar(id)); }

    @Transactional
    public void alternar(Long id) { Servico s = buscar(id); s.setAtivo(!s.isAtivo()); }

    private Servico buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado"));
    }
}
