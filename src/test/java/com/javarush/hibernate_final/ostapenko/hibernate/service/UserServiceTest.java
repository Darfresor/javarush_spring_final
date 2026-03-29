package com.javarush.hibernate_final.ostapenko.hibernate.service;


import com.javarush.hibernate_final.ostapenko.hibernate.entity.User;
import com.javarush.hibernate_final.ostapenko.hibernate.service.login.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebMvcTest
public class UserServiceTest {

    @Autowired
    private  UserService userService;

    @Test
    void givenExistingLoginName_whenFindByLoginName_thenReturnUser(){
        String userName = "user1";
        User userNameFound = userService.findByLoginName(userName).get();
        assertThat(userNameFound.getUserName()).isNotNull();

    }


}
