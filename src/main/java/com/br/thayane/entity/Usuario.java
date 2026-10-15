package com.br.thayane.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nome;
    @Column(nullable = false, unique = true, length = 160)
    private String email;
    @Column(nullable = false)
    private String senha; // hash BCrypt
    @Column(nullable = false, length = 30)
    private String role = "ADMIN";
    private boolean ativo = true;
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
