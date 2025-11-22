package com.javarush.hibernate_final.ostapenko.hibernate;

import com.javarush.hibernate_final.ostapenko.hibernate.model.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestSpring implements CommandLineRunner {

    private final UserService userService;

    // Конструкторная инъекция
    public TestSpring(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        // Запускает Spring контекст!
        SpringApplication.run(TestSpring.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Этот метод выполняется после полной инициализации Spring
        System.out.println(userService.findByEmail("user@gmail.com"));
    }
}
