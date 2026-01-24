package com.javarush.hibernate_final.ostapenko.hibernate.controller.home;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeUIController {
    private static final Logger log = LoggerFactory.getLogger(HomeUIController.class);

   @GetMapping({"/", "ui/home"})
   public String home(Model model) {
       log.info("Начало работы контроллера home: {}", model);

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
        log.info("Завершение работы контроллера home: {}", model);
        return "pages/home"; // Ищет src/main/resources/templates/index.html
    }

}
