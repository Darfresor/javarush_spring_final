package com.javarush.hibernate_final.ostapenko.hibernate.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeUIController {

   @GetMapping({"/", "/home"})
   public String home(Model model) {
        model.addAttribute("appTitle", "Мое крутое приложение");
        model.addAttribute("welcomeMessage", "Добро пожаловать в нашу систему!");
        model.addAttribute("appName", "quest");
        model.addAttribute("isAdmin", true);
        model.addAttribute("userId", 12345);
        model.addAttribute("infoLinkText", "Подробная информация");

        // Список функций
        model.addAttribute("features", java.util.Arrays.asList(
                "Управление задачами",
                "Отчеты и аналитика",
                "Командная работа"
        ));

        return "pages/home"; // Ищет src/main/resources/templates/index.html
    }

}
