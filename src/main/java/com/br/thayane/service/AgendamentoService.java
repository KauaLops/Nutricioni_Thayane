package com.br.thayane.service;

import com.br.thayane.dto.AgendamentoDTO;
import com.br.thayane.exception.EnvioEmailException;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {
    private final ObjectProvider<JavaMailSender> mailSender;
    private final String destino;
    private final String remetente;

    public AgendamentoService(ObjectProvider<JavaMailSender> mailSender,
                              @Value("${app.mail.destino}") String destino,
                              @Value("${spring.mail.username:}") String remetente) {
        this.mailSender = mailSender; this.destino = destino; this.remetente = remetente;
    }

    public void enviar(AgendamentoDTO d) {
        JavaMailSender sender = mailSender.getIfAvailable();
        if (sender == null || remetente.isBlank()) {
            throw new EnvioEmailException("SMTP não configurado (defina MAIL_USERNAME e MAIL_PASSWORD).");
        }
        SimpleMailMessage m = new SimpleMailMessage();
        m.setFrom(remetente);
        m.setTo(destino);
        m.setReplyTo(limpa(d.getEmail()));
        m.setSubject("Nova solicitação de agendamento - " + limpa(d.getNome()));
        m.setText("""
            Nova solicitação de agendamento pelo site

            Nome: %s
            E-mail: %s
            Telefone: %s
            Tipo de atendimento: %s
            Data desejada: %s
            Horário desejado: %s

            Mensagem:
            %s
            """.formatted(limpa(d.getNome()), limpa(d.getEmail()), limpa(d.getTelefone()), limpa(d.getTipo()),
                d.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), d.getHorario(),
                d.getMensagem() == null || d.getMensagem().isBlank() ? "(sem observações)" : d.getMensagem()));
        sender.send(m);
    }

    /** Remove quebras de linha para impedir injeção de cabeçalhos no e-mail. */
    private static String limpa(String s) { return s == null ? "" : s.replaceAll("[\\r\\n]+", " ").trim(); }
}
