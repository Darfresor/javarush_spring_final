package com.javarush.hibernate_final.ostapenko.hibernate.model.service.login;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.User;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByLoginName(String login) {
        return userRepository.findByLoginName(login);
    }
}
