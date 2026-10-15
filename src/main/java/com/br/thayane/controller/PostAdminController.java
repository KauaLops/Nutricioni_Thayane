package com.br.thayane.controller;

import com.br.thayane.dto.PostForm;
import com.br.thayane.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/admin/posts") @RequiredArgsConstructor
public class PostAdminController {
    private final PostService service;

    @GetMapping
    public String listar(@RequestParam(required = false) Long editar, Model m) {
        m.addAttribute("form", editar != null ? service.form(editar) : new PostForm());
        m.addAttribute("itens", service.listarTodos());
        return "admin/posts";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("form") PostForm form, BindingResult br, Model m, RedirectAttributes ra) {
        if (br.hasErrors()) { m.addAttribute("itens", service.listarTodos()); return "admin/posts"; }
        service.salvar(form);
        ra.addFlashAttribute("msg", "Publicação salva com sucesso.");
        return "redirect:/admin/posts";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluir(id); ra.addFlashAttribute("msg", "Publicação excluída."); return "redirect:/admin/posts";
    }
}
