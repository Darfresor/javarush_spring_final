package com.javarush.hibernate_final.ostapenko.hibernate.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);

            if (jwt != null && StringUtils.hasText(jwt)) {
                if (jwtService.validateToken(jwt)) { // Изменили метод валидации
                    String username = jwtService.extractUsername(jwt);
                    List<String> roles = jwtService.extractRoles(jwt);

                    if (StringUtils.hasText(username)) {
                        // Создаем authorities из ролей
                        List<GrantedAuthority> authorities = roles.stream()
                                .map(role -> new SimpleGrantedAuthority(role))
                                .collect(Collectors.toList());

                        // Создаем аутентификацию
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        username,           // principal как строка
                                        null,               // credentials
                                        authorities         // authorities из токена
                                );

                        // Устанавливаем аутентификацию в контекст
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);

                        logger.debug("✅ JWT authenticated for user: " + username);
                    } else {
                        SecurityContextHolder.clearContext();
                        logger.debug("❌ Could not extract username from JWT");
                    }
                } else {
                    SecurityContextHolder.clearContext();
                    logger.debug("❌ JWT token invalid");
                }
            } else {
                SecurityContextHolder.clearContext();
                logger.debug("⚠️ No JWT token found");
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();

            if (logger.isDebugEnabled()) {
                logger.debug("JWT authentication exception: " + e.getMessage(), e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        // 1. Пробуем из заголовка Authorization
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7);
            logger.debug("Found JWT in Authorization header");
            return token;
        }

        // 2. Пробуем из Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (StringUtils.hasText(token)) {
                        logger.debug("Found JWT in cookie");
                        return token;
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/bootstrap/") ||
                path.startsWith("/jquery/") ||
                path.equals("/favicon.ico") ||
                path.equals("/") ||
                path.startsWith("/api/auth/") ||
                path.startsWith("/ui/login") ||
                path.startsWith("/ui/register");
    }
}