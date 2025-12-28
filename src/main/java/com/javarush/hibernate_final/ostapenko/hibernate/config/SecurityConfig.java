package com.javarush.hibernate_final.ostapenko.hibernate.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                        .permitAll()
                )
                // CSRF включаем (для форм Spring Security)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // для API можно отключить
                );
        return http.build();
    }

    /*для ручной проверки пользователей из памяти
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();
        //System.out.println(passwordEncoder().encode("admin123"));

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();
        //System.out.println(passwordEncoder().encode("user123"));

        return new InMemoryUserDetailsManager(admin, user);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
