package com.br.thayane.controller;

import com.br.thayane.dto.ContatoForm;
import com.br.thayane.dto.RedeForm;
import com.br.thayane.service.ContatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/admin/configuracoes") @RequiredArgsConstructor
public class ConfiguracaoAdminController {
    private final ContatoService service;

    @GetMapping
    public String tela(@RequestParam(required = false) Long editarRede, Model m) {
        m.addAttribute("contatoForm", service.form());
        m.addAttribute("redeForm", editarRede != null ? service.formRede(editarRede) : new RedeForm());
        m.addAttribute("itens", service.todasRedes());
        return "admin/configuracoes";
    }

    @PostMapping("/contato")
    public String salvarContato(@Valid @ModelAttribute("contatoForm") ContatoForm form, BindingResult br, Model m, RedirectAttributes ra) {
        if (br.hasErrors()) {
            m.addAttribute("redeForm", new RedeForm()); m.addAttribute("itens", service.todasRedes());
            return "admin/configuracoes";
        }
        service.salvar(form);
        ra.addFlashAttribute("msg", "Informações de contato atualizadas.");
        return "redirect:/admin/configuracoes";
    }

    @PostMapping("/redes/salvar")
    public String salvarRede(@Valid @ModelAttribute("redeForm") RedeForm form, BindingResult br, Model m, RedirectAttributes ra) {
        if (br.hasErrors()) {
            m.addAttribute("contatoForm", service.form()); m.addAttribute("itens", service.todasRedes());
            return "admin/configuracoes";
        }
        service.salvarRede(form);
        ra.addFlashAttribute("msg", "Rede social salva.");
        return "redirect:/admin/configuracoes";
    }

    @PostMapping("/redes/{id}/excluir")
    public String excluirRede(@PathVariable Long id, RedirectAttributes ra) {
        service.excluirRede(id); ra.addFlashAttribute("msg", "Rede removida."); return "redirect:/admin/configuracoes";
    }
}
