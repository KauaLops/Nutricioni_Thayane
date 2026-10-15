package com.br.thayane.controller;

import com.br.thayane.dto.ServicoForm;
import com.br.thayane.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/admin/precos") @RequiredArgsConstructor
public class ServicoAdminController {
    private final ServicoService service;

    @GetMapping
    public String listar(@RequestParam(required = false) Long editar, Model m) {
        m.addAttribute("form", editar != null ? service.form(editar) : new ServicoForm());
        m.addAttribute("itens", service.listarTodos());
        return "admin/precos";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("form") ServicoForm form, BindingResult br, Model m, RedirectAttributes ra) {
        if (br.hasErrors()) { m.addAttribute("itens", service.listarTodos()); return "admin/precos"; }
        service.salvar(form);
        ra.addFlashAttribute("msg", "Serviço salvo com sucesso.");
        return "redirect:/admin/precos";
    }

    @PostMapping("/{id}/alternar")
    public String alternar(@PathVariable Long id, RedirectAttributes ra) {
        service.alternar(id); ra.addFlashAttribute("msg", "Status atualizado."); return "redirect:/admin/precos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluir(id); ra.addFlashAttribute("msg", "Serviço excluído."); return "redirect:/admin/precos";
    }
}
