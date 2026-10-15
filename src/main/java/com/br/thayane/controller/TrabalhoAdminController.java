package com.br.thayane.controller;

import com.br.thayane.dto.TrabalhoForm;
import com.br.thayane.service.TrabalhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/admin/trabalhos") @RequiredArgsConstructor
public class TrabalhoAdminController {
    private final TrabalhoService service;

    @GetMapping
    public String listar(@RequestParam(required = false) Long editar, Model m) {
        m.addAttribute("form", editar != null ? service.form(editar) : new TrabalhoForm());
        m.addAttribute("itens", service.listarTodos());
        return "admin/trabalhos";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("form") TrabalhoForm form, BindingResult br, Model m, RedirectAttributes ra) {
        if (br.hasErrors()) { m.addAttribute("itens", service.listarTodos()); return "admin/trabalhos"; }
        service.salvar(form);
        ra.addFlashAttribute("msg", "Trabalho salvo com sucesso.");
        return "redirect:/admin/trabalhos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluir(id); ra.addFlashAttribute("msg", "Trabalho excluído."); return "redirect:/admin/trabalhos";
    }
}
