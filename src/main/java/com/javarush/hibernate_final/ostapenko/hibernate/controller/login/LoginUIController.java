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
            Model model
    ){
        return "pages/login";
    }
}
