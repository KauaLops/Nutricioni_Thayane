package com.br.thayane.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;

@Getter @Setter
public class ServicoForm {
    private Long id;
    @NotBlank(message = "Informe o nome do serviço") @Size(max = 120) private String nome;
    @NotBlank(message = "Informe a descrição") @Size(max = 500) private String descricao;
    @NotNull(message = "Informe o preço") @DecimalMin(value = "0.00", message = "O preço não pode ser negativo")
    @Digits(integer = 8, fraction = 2) private BigDecimal preco;
    @Size(max = 1000, message = "Máximo de 1000 caracteres") private String beneficios;
    private boolean ativo = true;
}
