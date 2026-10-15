package com.br.thayane.entity;

import jakarta.persistence.*;
import lombok.*;

/** Registro único (id = 1) com os dados de contato e do portfólio. */
@Entity @Getter @Setter @NoArgsConstructor
public class Contato {
    @Id
    private Long id = 1L;
    @Column(length = 160) private String email;
    @Column(length = 40) private String telefone;
    @Column(length = 80) private String instagram;
    @Column(length = 40) private String whatsapp;
    @Column(length = 20) private String crn;

    public String getWhatsappDigitos() {
        return whatsapp == null ? "" : whatsapp.replaceAll("\\D", "");
    }
    public String getWhatsappLink() {
        return "https://wa.me/" + getWhatsappDigitos() + "?text=Ol%C3%A1%2C%20gostaria%20de%20agendar%20uma%20consulta.";
    }
    public String getInstagramUsuario() {
        return instagram == null ? "" : instagram.trim().replaceFirst("^@", "");
    }
    public String getInstagramLink() { return "https://instagram.com/" + getInstagramUsuario(); }
}
