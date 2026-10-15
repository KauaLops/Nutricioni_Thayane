package com.br.thayane.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
public class Trabalho {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 160)
    private String titulo;
    @Column(length = 1000)
    private String descricao;
    @Column(length = 60)
    private String categoria;
    @Column(length = 500)
    private String imagemUrl;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private boolean ativo = true;
}
