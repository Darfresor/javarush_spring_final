package com.javarush.hibernate_final.ostapenko.hibernate;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.QuestService;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.data.domain.Page;

//@SpringBootApplication
public class TestSpring implements CommandLineRunner {

    private  UserService userService;
    private  QuestService questService;


    public TestSpring(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public TestSpring(QuestService questService) {
        this.questService = questService;
    }

    public static void main(String[] args) {
        // Запускает Spring контекст!
        SpringApplication.run(TestSpring.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Этот метод выполняется после полной инициализации Spring
        //System.out.println(userService.findByEmail("user@gmail.com"));
       Page<QuestTo> page =  questService.getQuests(0, 10);
       page.getContent().forEach(quest->{
           System.out.println(quest);
       });
    }
}
