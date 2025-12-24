package com.javarush.hibernate_final.ostapenko.hibernate.controller.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "remember-me", required = false) Boolean rememberMe
    ) {

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Remember me: " + rememberMe);

        return ResponseEntity.status(302)
                .header("Location", "/ui/home")
                .build();
    }
}
