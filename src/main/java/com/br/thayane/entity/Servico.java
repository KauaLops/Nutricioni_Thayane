package com.br.thayane.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
public class Servico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nome;
    @Column(length = 500)
    private String descricao;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    /** Um benefício por linha. */
    @Column(length = 1000)
    private String beneficios;
    private boolean ativo = true;

    public List<String> getListaBeneficios() {
        if (beneficios == null || beneficios.isBlank()) return List.of();
        return Arrays.stream(beneficios.split("\\R")).map(String::trim).filter(s -> !s.isEmpty()).toList();
    }
}
