package com.javarush.hibernate_final.ostapenko.hibernate.controller.login;

import com.javarush.hibernate_final.ostapenko.hibernate.model.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    //@Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //@PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "remember-me", required = false) Boolean rememberMe
    ) {

        boolean isExistsUser = loginService.checkUserAndPassword(username, password);
        System.out.println(isExistsUser);

        return ResponseEntity.status(302)
                .header("Location", "/ui/home")
                .build();
    }
}
