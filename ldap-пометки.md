package com.javarush.hibernate_final.ostapenko.hibernate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

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
                                "/public",
                                "/test/userinfo",  // для теста
                                "/test/ldap-check" // для теста LDAP
                        ).permitAll()
                        .requestMatchers("/secure").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/ui/login")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/ui/home", true)
                        .failureUrl("/ui/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/ui/login?logout=true")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                );
        return http.build();
    }
    
    // LDAP Configuration
    
    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        // Замени на свои настройки LDAP/AD
        DefaultSpringSecurityContextSource contextSource = 
                new DefaultSpringSecurityContextSource(
                    Collections.singletonList("ldap://localhost:389"), 
                    "dc=example,dc=com"
                );
        
        // Сервисный пользователь (если нужен для поиска)
        contextSource.setUserDn("cn=admin,dc=example,dc=com");
        contextSource.setPassword("admin");
        contextSource.afterPropertiesSet();
        
        return contextSource;
    }
    
    @Bean
    public FilterBasedLdapUserSearch userSearch() {
        // Поиск пользователя в LDAP
        return new FilterBasedLdapUserSearch(
                "ou=people",                 // Базовый DN для поиска пользователей
                "(uid={0})",                 // Фильтр поиска (uid для OpenLDAP)
                contextSource()
        );
    }
    
    @Bean
    public LdapAuthenticator ldapAuthenticator() {
        BindAuthenticator authenticator = new BindAuthenticator(contextSource());
        authenticator.setUserSearch(userSearch());
        // Или используй шаблоны DN (если не используешь поиск):
        // authenticator.setUserDnPatterns(new String[]{"uid={0},ou=people"});
        return authenticator;
    }
    
    @Bean
    public DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
        // Получение ролей/групп из LDAP
        return new DefaultLdapAuthoritiesPopulator(
                contextSource(),
                "ou=groups"  // Базовый DN для поиска групп
        );
    }
    
    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider() {
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(
                ldapAuthenticator(),
                ldapAuthoritiesPopulator()
        );
        return provider;
    }
    
    // Для тестирования можно временно оставить BCrypt для других целей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // Для отладки - если нужен AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> ldapAuthenticationProvider().authenticate(authentication);
    }
}

------------------------------------
package com.javarush.hibernate_final.ostapenko.hibernate.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/userinfo")
    public Map<String, Object> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userInfo = new HashMap<>();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            userInfo.put("status", "Не аутентифицирован");
            return userInfo;
        }
        
        Object principal = authentication.getPrincipal();
        userInfo.put("authenticated", authentication.isAuthenticated());
        userInfo.put("authenticationClass", authentication.getClass().getSimpleName());
        userInfo.put("principalClass", principal.getClass().getSimpleName());
        userInfo.put("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            userInfo.put("username", userDetails.getUsername());
            userInfo.put("accountNonExpired", userDetails.isAccountNonExpired());
            userInfo.put("accountNonLocked", userDetails.isAccountNonLocked());
            userInfo.put("credentialsNonExpired", userDetails.isCredentialsNonExpired());
            userInfo.put("enabled", userDetails.isEnabled());
        } else if (principal instanceof String) {
            userInfo.put("username", principal.toString());
        }
        
        // Для LDAP пользователей
        if (principal instanceof LdapUserDetails) {
            LdapUserDetails ldapUser = (LdapUserDetails) principal;
            userInfo.put("ldapDn", ldapUser.getDn());
            userInfo.put("ldapAttributes", ldapUser.getAttributes() != null ? 
                    ldapUser.getAttributes().getAll().stream()
                            .collect(Collectors.toMap(
                                    attr -> attr.getID(),
                                    attr -> attr.get().toString()
                            )) : "No attributes");
        }
        
        return userInfo;
    }
    
    @GetMapping("/current-user")
    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && 
            !"anonymousUser".equals(auth.getPrincipal())) {
            
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>Текущий пользователь:</h3>");
            sb.append("<p>Имя: ").append(auth.getName()).append("</p>");
            sb.append("<p>Роли: ").append(auth.getAuthorities()).append("</p>");
            sb.append("<p>Класс: ").append(auth.getPrincipal().getClass().getSimpleName()).append("</p>");
            
            if (auth.getPrincipal() instanceof LdapUserDetails) {
                LdapUserDetails ldapUser = (LdapUserDetails) auth.getPrincipal();
                sb.append("<p>LDAP DN: ").append(ldapUser.getDn()).append("</p>");
            }
            
            return sb.toString();
        }
        return "Пользователь не аутентифицирован";
    }
}