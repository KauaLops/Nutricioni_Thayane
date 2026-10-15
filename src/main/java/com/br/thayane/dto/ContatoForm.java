package com.br.thayane.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class ContatoForm {
    @NotBlank @Email @Size(max = 160) private String email;
    @Size(max = 40) private String telefone;
    @Size(max = 80) private String instagram;
    @NotBlank(message = "Informe o WhatsApp") @Pattern(regexp = "^[0-9()+\\-\\s]{8,25}$", message = "WhatsApp inválido") private String whatsapp;
    @Size(max = 20) private String crn;
}
