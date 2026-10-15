package com.br.thayane.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@Getter @Setter
public class AgendamentoDTO {
    @NotBlank(message = "Informe seu nome completo") @Size(max = 120) private String nome;
    @NotBlank(message = "Informe seu e-mail") @Email(message = "E-mail inválido") @Size(max = 160) private String email;
    @NotBlank(message = "Informe seu telefone") @Pattern(regexp = "^[0-9()+\\-\\s]{8,20}$", message = "Telefone inválido") private String telefone;
    @NotNull(message = "Informe a data desejada") @FutureOrPresent(message = "A data deve ser hoje ou futura") private LocalDate data;
    @NotBlank(message = "Informe o horário") @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário inválido") private String horario;
    @NotBlank(message = "Informe o tipo de atendimento") @Size(max = 120) private String tipo;
    @Size(max = 1000, message = "Máximo de 1000 caracteres") private String mensagem;
}
