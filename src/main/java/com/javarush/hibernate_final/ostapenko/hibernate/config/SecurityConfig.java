package com.javarush.hibernate_final.ostapenko.hibernate.config;


import com.javarush.hibernate_final.ostapenko.hibernate.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF для JWT
                .csrf(csrf -> csrf.disable())
                // Настраиваем сессии как STATELESS (без сохранения состояния)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
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
                                "/bootstrap/**",
                                "/public",
                                "/api/auth/**",

                                "/debug/**"
                        ).permitAll()
                        .requestMatchers("/secure").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                /*
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
                */
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
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

}
