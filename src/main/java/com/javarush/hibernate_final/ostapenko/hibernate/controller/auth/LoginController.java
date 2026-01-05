package com.javarush.hibernate_final.ostapenko.hibernate.controller.auth;

import com.javarush.hibernate_final.ostapenko.hibernate.model.service.login.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

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
