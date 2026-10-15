package com.br.thayane.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
public class PostForm {
    private Long id;
    @NotBlank(message = "Informe o título") @Size(max = 160) private String titulo;
    @NotBlank(message = "Informe o resumo") @Size(max = 500) private String resumo;
    @NotBlank(message = "Informe o texto") @Size(max = 20000) private String conteudo;
    @Size(max = 500) private String imagemUrl;
    @NotBlank(message = "Informe a categoria") @Size(max = 60) private String categoria;
    @NotNull(message = "Informe a data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) private LocalDate dataPublicacao = LocalDate.now();
    private boolean ativo = true;
}
