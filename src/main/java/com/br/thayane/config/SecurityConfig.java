package com.br.thayane.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(12); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(a -> a
                .requestMatchers("/admin/login").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll())
            .formLogin(f -> f
                .loginPage("/admin/login").loginProcessingUrl("/admin/login")
                .usernameParameter("email").passwordParameter("senha")
                .defaultSuccessUrl("/admin", true).failureUrl("/admin/login?erro"))
            .logout(l -> l.logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login?logout")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID"))
            // CSRF permanece ativo; o formulário público envia o token no cabeçalho X-CSRF-TOKEN.
            .headers(h -> h
                .contentSecurityPolicy(c -> c.policyDirectives(
                    "default-src 'self'; script-src 'self'; style-src 'self' https://fonts.googleapis.com; "
                    + "font-src https://fonts.gstatic.com; img-src 'self' data: https:; frame-ancestors 'none'; form-action 'self'"))
                .frameOptions(fo -> fo.deny())
                .referrerPolicy(r -> r.policy(org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)));
        return http.build();
    }
}
