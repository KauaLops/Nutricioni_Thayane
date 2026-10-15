package com.br.thayane.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/** Tratamento global. Erros não mapeados caem na página /error (templates/error.html). */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> erros.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("message", "Revise os campos destacados.", "erros", erros));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> corpoInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", "Dados inválidos. Revise o formulário."));
    }

    @ExceptionHandler({MailException.class, EnvioEmailException.class})
    public ResponseEntity<Map<String, Object>> email(Exception ex) {
        log.error("Falha ao enviar e-mail de agendamento", ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("message",
            "Não foi possível enviar sua solicitação agora. Tente novamente em instantes ou fale pelo WhatsApp."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView integridade(DataIntegrityViolationException ex) {
        log.warn("Violação de integridade", ex);
        ModelAndView mv = new ModelAndView("error", HttpStatus.CONFLICT);
        mv.addObject("status", 409);
        mv.addObject("error", "Não foi possível concluir a operação: dados em conflito.");
        return mv;
    }
}
