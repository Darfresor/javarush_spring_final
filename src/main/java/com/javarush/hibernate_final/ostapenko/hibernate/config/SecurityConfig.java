package com.javarush.hibernate_final.ostapenko.hibernate.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/ui/home",
                                "/ui/login",
                                "/bootstrap/**",
                                "/public"
                        ).permitAll()
                        .requestMatchers("/secure").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/ui/login")
                        .loginProcessingUrl("/perform_login") // куда отправлять форму
                        .usernameParameter("username")   // имя поля пользователя из формы
                        .passwordParameter("password")   // имя поля пароля из формы
                        .defaultSuccessUrl("/ui/home", true) // после успеха
                        .failureUrl("/ui/login?error=true")  // при ошибке
                        .permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/ui/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecretKey")
                        .tokenValiditySeconds(86400) // 24 часа
                )
                // CSRF включаем (для форм Spring Security)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // для API можно отключить
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
