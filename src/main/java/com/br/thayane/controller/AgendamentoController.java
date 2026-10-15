package com.br.thayane.controller;

import com.br.thayane.dto.AgendamentoDTO;
import com.br.thayane.service.AgendamentoService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/agendamento") @RequiredArgsConstructor
public class AgendamentoController {
    private final AgendamentoService service;

    @PostMapping
    public ResponseEntity<Map<String, String>> solicitar(@Valid @RequestBody AgendamentoDTO dto) {
        service.enviar(dto);
        return ResponseEntity.ok(Map.of("message",
            "Sua solicitação foi enviada com sucesso. Entraremos em contato para confirmar o atendimento."));
    }
}
