package com.br.thayane.controller;

import com.br.thayane.service.ContatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Disponibiliza contato e redes (cabeçalho/rodapé) em todas as páginas públicas. */
@ControllerAdvice(assignableTypes = PublicController.class)
@RequiredArgsConstructor
public class SiteAdvice {
    private final ContatoService contatos;

    @ModelAttribute
    public void dadosDoSite(Model m) {
        m.addAttribute("contato", contatos.obter());
        m.addAttribute("redes", contatos.redesAtivas());
    }
}
