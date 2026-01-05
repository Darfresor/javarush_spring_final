package com.javarush.hibernate_final.ostapenko.hibernate.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginUIController {

    public LoginUIController() {
    }
    @GetMapping("/ui/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ){
        if (error != null) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль!");
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "Вы успешно вышли из системы!");
        }
        return "pages/login";
    }
}
