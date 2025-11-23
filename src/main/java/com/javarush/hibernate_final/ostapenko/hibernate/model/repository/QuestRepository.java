package com.javarush.hibernate_final.ostapenko.hibernate.model.repository;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    Page<Quest> findAll(Pageable pageable);
}
