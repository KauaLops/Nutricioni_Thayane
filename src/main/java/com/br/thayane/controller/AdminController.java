package com.br.thayane.controller;

import com.br.thayane.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller @RequiredArgsConstructor
public class AdminController {
    private final ServicoService servicos; private final PostService posts; private final TrabalhoService trabalhos;

    @GetMapping("/admin/login")
    public String login() { return "admin/login"; }

    @GetMapping("/admin")
    public String dashboard(Model m) {
        m.addAttribute("qtServicos", servicos.listarTodos().size());
        m.addAttribute("qtPosts", posts.listarTodos().size());
        m.addAttribute("qtTrabalhos", trabalhos.listarTodos().size());
        return "admin/dashboard";
    }
}
