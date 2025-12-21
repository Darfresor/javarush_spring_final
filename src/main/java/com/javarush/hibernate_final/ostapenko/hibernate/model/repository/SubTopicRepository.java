package com.javarush.hibernate_final.ostapenko.hibernate.model.repository;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.SubTopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.SubTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




@Repository
public interface SubTopicRepository extends JpaRepository<SubTopic, Long> {

    Page<SubTopic> findAllByTopicId(Long topicId, Pageable pageable);

    @Query("SELECT st.id as id, st.name as name, st.topic.id as topicId, COUNT(p) as postsCount  " +
            "FROM SubTopic st LEFT JOIN st.postList p " +
            "where st.topic.id = :topicId " +
            "GROUP BY st.id, st.name")
    Page<SubTopicWithCountTo> findAllByTopicIdWithCount(@Param("topicId") Long topicId, Pageable pageable);

}
