package com.javarush.hibernate_final.ostapenko.hibernate.repository;

import com.javarush.hibernate_final.ostapenko.hibernate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String login);
}
