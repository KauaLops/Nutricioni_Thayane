package com.br.thayane.entity;

import jakarta.persistence.*;
import lombok.*;

/** Redes/canais adicionais cadastrados pelo administrador. */
@Entity @Getter @Setter @NoArgsConstructor
public class RedeSocial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60) private String nome;
    @Column(nullable = false, length = 300) private String url;
    private boolean ativo = true;
}
