package com.javarush.hibernate_final.ostapenko.hibernate.security.jwt;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityDialect implements IExpressionObjectDialect{
    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("jwt");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                if ("jwt".equals(expressionObjectName)) {
                    return new JwtSecurityExpressionObject();
                }
                return null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return false; // Не кэшировать, т.к. SecurityContext меняется
            }
        };
    }

    @Override
    public String getName() {
        return "JwtSecurityDialect";
    }

    // Внутренний класс с методами для Thymeleaf
    public static class JwtSecurityExpressionObject {

        public boolean isAuthenticated() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // Проверяем, что аутентификация не null, не анонимная и действительно аутентифицирована
            return auth != null
                    && auth.isAuthenticated()
                    && !(auth instanceof AnonymousAuthenticationToken);
        }

        public String getUsername() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // Возвращаем имя только если это не анонимный пользователь
            if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
                return auth.getName();
            }
            return null;
        }

        public boolean hasRole(String role) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth instanceof AnonymousAuthenticationToken) {
                return false;
            }

            return auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role::equals);
        }

        public boolean hasAnyRole(String... roles) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth instanceof AnonymousAuthenticationToken) {
                return false;
            }

            for (String role : roles) {
                if (auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(role::equals)) {
                    return true;
                }
            }
            return false;
        }

        // Новый метод для получения списка всех ролей пользователя
        public List<String> getRoles() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth instanceof AnonymousAuthenticationToken) {
                return Collections.emptyList();
            }

            return auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }

        // Дополнительный метод для проверки всех ролей сразу
        public boolean hasAllRoles(String... roles) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth instanceof AnonymousAuthenticationToken) {
                return false;
            }

            Set<String> userRoles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            for (String role : roles) {
                if (!userRoles.contains(role)) {
                    return false;
                }
            }
            return true;
        }
    }
}