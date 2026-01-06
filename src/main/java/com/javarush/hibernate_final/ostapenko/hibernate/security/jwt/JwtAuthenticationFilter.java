package com.javarush.hibernate_final.ostapenko.hibernate.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);

            if (jwt != null && StringUtils.hasText(jwt)) {
                String username = jwtService.extractUsername(jwt);

                if (StringUtils.hasText(username)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.validateToken(jwt, userDetails)) {
                        // ✅ КРИТИЧЕСКИ: Всегда создаем новый контекст для STATELESS
                        SecurityContext context = SecurityContextHolder.createEmptyContext();

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,        // principal
                                        null,               // credentials (не нужно для JWT)
                                        userDetails.getAuthorities() // authorities
                                );


                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);
                    } else {
                        // Токен невалиден - очищаем контекст
                        SecurityContextHolder.clearContext();
                        logger.debug("❌ JWT token invalid, clearing context");
                    }
                } else {
                    // Не удалось извлечь username - очищаем контекст
                    SecurityContextHolder.clearContext();
                }
            } else {
                // Нет токена - очищаем контекст
                SecurityContextHolder.clearContext();
                logger.debug("⚠️ No JWT token found");
            }

        } catch (Exception e) {
            // Любая ошибка - очищаем контекст
            SecurityContextHolder.clearContext();

            // Можно логировать подробнее для отладки
            if (logger.isDebugEnabled()) {
                logger.debug("JWT authentication exception", e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        // 1. Пробуем из заголовка Authorization (для AJAX/API запросов)
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7);
            logger.debug("Found JWT in Authorization header");
            return token;
        }

        // 2. Пробуем из Cookie (для обычных браузерных переходов)
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    return token;
                }
            }
        }

        logger.debug("No JWT found in request");
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Можно исключить публичные эндпоинты для оптимизации
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