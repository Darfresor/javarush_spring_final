package com.javarush.hibernate_final.ostapenko.hibernate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/public")
    public String publicEndpoint() {
        return "Этот эндпоинт доступен всем!";
    }

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "Вы зашли в защищённую зону!";
    }
}
