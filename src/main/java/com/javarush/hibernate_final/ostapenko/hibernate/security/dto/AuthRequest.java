package com.javarush.hibernate_final.ostapenko.hibernate.security.dto;

import jakarta.validation.constraints.Size;
import lombok.ToString;

@ToString
public class AuthRequest {

    private String username;
    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;

    // Геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
