package com.javarush.hibernate_final.ostapenko.hibernate.controller.login;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.SubTopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.DTO.TopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Post;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.PostService;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.SubTopicService;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
