package com.javarush.hibernate_final.ostapenko.hibernate.config;

import com.javarush.hibernate_final.ostapenko.hibernate.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ПОЛНОСТЬЮ отключаем CSRF для JWT (важно для работы без сессии)
                .csrf(csrf -> csrf.disable()) // ← меняем с ignoring на disable

                // Настраиваем сессии как STATELESS (без сохранения состояния)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionFixation().none() // ← добавляем это
                )

                // ✅ КРИТИЧЕСКИ ВАЖНО для работы с Thymeleaf без сессии
                .securityContext(context -> context
                        .securityContextRepository(new RequestAttributeSecurityContextRepository())
                        .requireExplicitSave(false)
                )

                // Отключаем request cache
                .requestCache(cache -> cache.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/jquery/**",
                                "/js/**",
                                "/css/**",
                                "/bootstrap/**",
                                "/images/**",
                                "/favicon.ico",
                                "/",
                                "/ui/home",
                                "/ui/login",
                                "/ui/register", // ← добавьте если есть регистрация
                                "/bootstrap/**",
                                "/public",
                                "/api/auth/**",
                                "/debug/**"
                        ).permitAll()
                        .requestMatchers("/secure").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}