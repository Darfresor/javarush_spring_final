package com.javarush.hibernate_final.ostapenko.hibernate.controller.home;

import com.javarush.hibernate_final.ostapenko.hibernate.service.metrics.CustomMetricsService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeUIController implements InitializingBean {

    private final CustomMetricsService customMetricsService;

    public HomeUIController(CustomMetricsService customMetricsService) {
        this.customMetricsService = customMetricsService;
    }

    @PostConstruct
    public void init() {
        log.info("Бин HomeUIController создан и готов к использованию!");
    }



   @GetMapping({"/", "ui/home"})
   @Counted(value = "custom.metric.counter",
           extraTags = {"type", "custom_metric", "method", "home"})
   @Timed(value = "custom.metric.time",
           extraTags = {"type", "home_page"})
   public String home(Model model) {
       customMetricsService.performAction();

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

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Все свойства Бин HomeUIController установлены и он готов к работе, можно проводить доп.настройки. ");
    }
}
