package com.javarush.hibernate_final.ostapenko.hibernate.security.controller;

import com.javarush.hibernate_final.ostapenko.hibernate.security.dto.AuthRequest;
import com.javarush.hibernate_final.ostapenko.hibernate.security.dto.AuthResponse;
import com.javarush.hibernate_final.ostapenko.hibernate.security.jwt.JwtService;
import jakarta.servlet.http.Cookie; // ← Добавьте
import jakarta.servlet.http.HttpServletResponse; // ← Убедитесь, что есть
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest,
                                   HttpServletResponse response) { // ← Добавьте параметр
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            // Устанавливаем Cookie
            Cookie jwtCookie = new Cookie("jwtToken", token);
            jwtCookie.setHttpOnly(true); // Защита от XSS
            jwtCookie.setSecure(false); // Поставьте true в production с HTTPS
            jwtCookie.setPath("/"); // Доступно для всего сайта
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 часа в секундах
            response.addCookie(jwtCookie);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Удаляем Cookie
        Cookie jwtCookie = new Cookie("jwtToken", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Немедленное удаление
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}