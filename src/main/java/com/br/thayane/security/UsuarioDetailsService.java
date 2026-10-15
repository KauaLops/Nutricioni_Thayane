package com.br.thayane.security;

import com.br.thayane.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmailIgnoreCase(email.trim())
            .map(u -> User.withUsername(u.getEmail()).password(u.getSenha()).roles(u.getRole()).disabled(!u.isAtivo()).build())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
