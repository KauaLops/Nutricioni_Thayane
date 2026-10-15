package com.br.thayane.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class TrabalhoForm {
    private Long id;
    @NotBlank(message = "Informe o título") @Size(max = 160) private String titulo;
    @Size(max = 1000) private String descricao;
    @NotBlank(message = "Informe a categoria") @Size(max = 60) private String categoria;
    @Size(max = 500) private String imagemUrl;
    private boolean ativo = true;
}
