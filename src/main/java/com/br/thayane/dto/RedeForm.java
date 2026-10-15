package com.br.thayane.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class RedeForm {
    private Long id;
    @NotBlank(message = "Informe o nome da rede") @Size(max = 60) private String nome;
    @NotBlank(message = "Informe o link") @Size(max = 300)
    @Pattern(regexp = "^https?://.+", message = "O link deve começar com http:// ou https://") private String url;
    private boolean ativo = true;
}
