package com.javarush.hibernate_final.ostapenko.hibernate.model.repository;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.TopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findAll(Pageable pageable);

    @Query("SELECT t.id as id, t.name as name,COUNT(st) as subtopicCount FROM Topic t LEFT JOIN t.subTopic  st GROUP BY t.id, t.name")
    Page<TopicWithCountTo> findAllTopicsWithCount(Pageable pageable);

    @Query("select count(st) FROM Topic t LEFT JOIN t.subTopic st where t.id = :topicId")
    Long countSubtopicByTopicId(@Param("topicId") Long topicId);

}
