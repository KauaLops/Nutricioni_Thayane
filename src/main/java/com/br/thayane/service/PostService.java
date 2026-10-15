package com.br.thayane.service;

import com.br.thayane.dto.PostForm;
import com.br.thayane.entity.Post;
import com.br.thayane.exception.RecursoNaoEncontradoException;
import com.br.thayane.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class PostService {
    private final PostRepository repo;

    public List<Post> listarPublicados() { return repo.findByAtivoTrueOrderByDataPublicacaoDescIdDesc(); }
    public List<Post> listarTodos() { return repo.findAll(Sort.by(Sort.Direction.DESC, "dataPublicacao", "id")); }

    public Post buscarPublicado(Long id) {
        return repo.findByIdAndAtivoTrue(id).orElseThrow(() -> new RecursoNaoEncontradoException("Publicação não encontrada"));
    }

    public PostForm form(Long id) {
        Post p = buscar(id);
        PostForm f = new PostForm();
        f.setId(p.getId()); f.setTitulo(p.getTitulo()); f.setResumo(p.getResumo()); f.setConteudo(p.getConteudo());
        f.setImagemUrl(p.getImagemUrl()); f.setCategoria(p.getCategoria());
        f.setDataPublicacao(p.getDataPublicacao()); f.setAtivo(p.isAtivo());
        return f;
    }

    @Transactional
    public void salvar(PostForm f) {
        Post p = f.getId() == null ? new Post() : buscar(f.getId());
        p.setTitulo(f.getTitulo().trim()); p.setResumo(f.getResumo().trim()); p.setConteudo(f.getConteudo());
        p.setImagemUrl(f.getImagemUrl() == null || f.getImagemUrl().isBlank() ? "/images/blog-1.svg" : f.getImagemUrl().trim());
        p.setCategoria(f.getCategoria().trim()); p.setDataPublicacao(f.getDataPublicacao()); p.setAtivo(f.isAtivo());
        repo.save(p);
    }

    @Transactional public void excluir(Long id) { repo.delete(buscar(id)); }

    private Post buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Publicação não encontrada"));
    }
}
