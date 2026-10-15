package com.br.thayane.service;

import com.br.thayane.dto.ContatoForm;
import com.br.thayane.dto.RedeForm;
import com.br.thayane.entity.Contato;
import com.br.thayane.entity.RedeSocial;
import com.br.thayane.exception.RecursoNaoEncontradoException;
import com.br.thayane.repository.ContatoRepository;
import com.br.thayane.repository.RedeSocialRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository contatos;
    private final RedeSocialRepository redes;

    public Contato obter() { return contatos.findById(1L).orElseGet(Contato::new); }

    public ContatoForm form() {
        Contato c = obter();
        ContatoForm f = new ContatoForm();
        f.setEmail(c.getEmail()); f.setTelefone(c.getTelefone()); f.setInstagram(c.getInstagram());
        f.setWhatsapp(c.getWhatsapp()); f.setCrn(c.getCrn());
        return f;
    }

    @Transactional
    public void salvar(ContatoForm f) {
        Contato c = obter();
        c.setId(1L); c.setEmail(f.getEmail().trim()); c.setTelefone(f.getTelefone());
        c.setInstagram(f.getInstagram() == null ? null : f.getInstagram().trim().replaceFirst("^@", ""));
        c.setWhatsapp(f.getWhatsapp().trim()); c.setCrn(f.getCrn());
        contatos.save(c);
    }

    public List<RedeSocial> redesAtivas() { return redes.findByAtivoTrueOrderByIdAsc(); }
    public List<RedeSocial> todasRedes() { return redes.findAll(Sort.by("id")); }

    public RedeForm formRede(Long id) {
        RedeSocial r = buscarRede(id);
        RedeForm f = new RedeForm();
        f.setId(r.getId()); f.setNome(r.getNome()); f.setUrl(r.getUrl()); f.setAtivo(r.isAtivo());
        return f;
    }

    @Transactional
    public void salvarRede(RedeForm f) {
        RedeSocial r = f.getId() == null ? new RedeSocial() : buscarRede(f.getId());
        r.setNome(f.getNome().trim()); r.setUrl(f.getUrl().trim()); r.setAtivo(f.isAtivo());
        redes.save(r);
    }

    @Transactional public void excluirRede(Long id) { redes.delete(buscarRede(id)); }

    private RedeSocial buscarRede(Long id) {
        return redes.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Rede não encontrada"));
    }
}
