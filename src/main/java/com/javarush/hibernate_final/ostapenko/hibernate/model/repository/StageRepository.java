package com.javarush.hibernate_final.ostapenko.hibernate.model.repository;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StageRepository extends JpaRepository<Stage, Long> {
    Optional<Stage> findByIdAndIsQuestIdRootTrue(Long id);

    @Query("SELECT s FROM Stage s " +
            "LEFT JOIN FETCH s.question q " +
            "LEFT JOIN FETCH q.answerList a "+
            "WHERE s.id = :id AND s.isQuestIdRoot = true")
    Optional<Stage> findByIdAndIsQuestIdRootTrueWithAnswers(Long id);

    @Query("SELECT s FROM Stage s " +
            "LEFT JOIN FETCH s.question q " +
            "LEFT JOIN FETCH q.answerList a "+
            "WHERE s.id = :id ")
    Optional<Stage> findByIdWithAnswers(Long id);
}
