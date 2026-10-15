package com.br.thayane.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 160)
    private String titulo;
    @Column(length = 500)
    private String resumo;
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    @Column(length = 500)
    private String imagemUrl;
    @Column(length = 60)
    private String categoria;
    private LocalDate dataPublicacao = LocalDate.now();
    private boolean ativo = true;
}
