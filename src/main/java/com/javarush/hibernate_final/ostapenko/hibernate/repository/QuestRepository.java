package com.javarush.hibernate_final.ostapenko.hibernate.repository;

import com.javarush.hibernate_final.ostapenko.hibernate.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    @Query("SELECT  q FROM Quest q LEFT JOIN FETCH q.stageList WHERE q.id = :questId")
    Optional<Quest> findByIdWithRootStage(Long id);

    @Query("SELECT  q FROM Quest q LEFT JOIN FETCH q.stageList WHERE q.id = :questId")
    Optional<Quest> findByIdWithStages(Long questId);

}
