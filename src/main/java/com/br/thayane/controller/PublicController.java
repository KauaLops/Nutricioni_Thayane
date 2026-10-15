package com.br.thayane.controller;

import com.br.thayane.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequiredArgsConstructor
public class PublicController {
    private final ServicoService servicos; private final PostService posts; private final TrabalhoService trabalhos;

    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("servicos", servicos.listarAtivos());
        m.addAttribute("posts", posts.listarPublicados());
        m.addAttribute("trabalhos", trabalhos.listarAtivos());
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String post(@PathVariable Long id, Model m) {
        m.addAttribute("post", posts.buscarPublicado(id));
        return "post";
    }

    @GetMapping("/termos")
    public String termos(Model m) { m.addAttribute("tipo", "termos"); return "legal"; }

    @GetMapping("/privacidade")
    public String privacidade(Model m) { m.addAttribute("tipo", "privacidade"); return "legal"; }
}
