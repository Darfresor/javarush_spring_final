package com.javarush.hibernate_final.ostapenko.hibernate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/check-user")
    public ResponseEntity<?> checkUser(
            @RequestParam(required = false, defaultValue = "admin") String username,
            @RequestParam(required = false, defaultValue = "admin123") String password) {

        System.out.println("\n=== 🔍 DEBUG CHECK USER ===");
        System.out.println("Testing username: " + username);
        System.out.println("Testing password: " + password);

        try {
            // 1. Пробуем найти пользователя
            System.out.println("1. Looking for user in database...");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            System.out.println("✅ SUCCESS: User found!");
            System.out.println("   - Username from DB: " + userDetails.getUsername());
            System.out.println("   - Password from DB: " + userDetails.getPassword());
            System.out.println("   - Is BCrypt? " + userDetails.getPassword().startsWith("$2a$"));

            // 2. Проверяем пароль
            System.out.println("2. Checking if password matches...");
            boolean passwordMatches = passwordEncoder.matches(password, userDetails.getPassword());

            System.out.println("🔑 Password check: " + (passwordMatches ? "✅ MATCHES" : "❌ DOES NOT MATCH"));

            // 3. Собираем результат
            Map<String, Object> result = new HashMap<>();
            result.put("status", passwordMatches ? "SUCCESS" : "WRONG_PASSWORD");
            result.put("userFound", true);
            result.put("username", userDetails.getUsername());
            result.put("passwordMatches", passwordMatches);
            result.put("isPasswordBCrypt", userDetails.getPassword().startsWith("$2a$"));
            result.put("passwordHashLength", userDetails.getPassword().length());
            result.put("authorities", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            result.put("message", passwordMatches ?
                    "User found and password is correct!" :
                    "User found but password is wrong!");

            System.out.println("3. Result: " + result);
            System.out.println("=== DEBUG COMPLETE ===\n");

            return ResponseEntity.ok(result);

        } catch (UsernameNotFoundException e) {
            System.out.println("❌ FAILED: User not found!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("=== DEBUG COMPLETE ===\n");

            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("status", "USER_NOT_FOUND");
            errorResult.put("userFound", false);
            errorResult.put("username", username);
            errorResult.put("message", "User '" + username + "' not found in database");

            return ResponseEntity.status(404).body(errorResult);
        }
    }

    // Тестовый эндпоинт для проверки существующих пользователей
    @GetMapping("/list-users")
    public ResponseEntity<?> listUsers() {
        try {
            // Попробуем найти несколько стандартных пользователей
            String[] testUsers = {"admin", "user", "test", "manager", "moderator"};

            Map<String, Object> result = new HashMap<>();
            result.put("message", "Testing common usernames");

            for (String username : testUsers) {
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    result.put(username, Map.of(
                            "exists", true,
                            "passwordHash", userDetails.getPassword().substring(0, Math.min(30, userDetails.getPassword().length())) + "...",
                            "isBCrypt", userDetails.getPassword().startsWith("$2a$")
                    ));
                } catch (UsernameNotFoundException e) {
                    result.put(username, Map.of("exists", false));
                }
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    // Простой эндпоинт для проверки, что сервис работает
    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        System.out.println("🏓 PING received - DebugController is working!");
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "service", "DebugController",
                "timestamp", System.currentTimeMillis()
        ));
    }
}