package com.javarush.hibernate_final.ostapenko.hibernate.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingUIController {
    @GetMapping("/ui/settings")
    public String showLoginPage(
            Model model
    ){

        return "pages/setting";
    }
}
