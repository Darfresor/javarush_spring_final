package com.javarush.hibernate_final.ostapenko.hibernate.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardUIController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "layout/main";
    }
}
