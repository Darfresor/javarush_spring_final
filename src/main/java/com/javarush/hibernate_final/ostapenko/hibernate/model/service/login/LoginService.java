package com.javarush.hibernate_final.ostapenko.hibernate.model.service.login;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
public class LoginService {
    private final UserService userService;

    //@Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public boolean checkUserAndPassword(String login, String password){
        Optional<User> optionalUser= userService.findByLoginName(login);
        if(optionalUser.isEmpty()){
            return false;
        }
        User user = optionalUser.get();

        return password.equals(user.getPassword());
    }
}
