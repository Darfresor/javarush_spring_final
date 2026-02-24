package com.javarush.hibernate_final.ostapenko.hibernate.repository;

import com.javarush.hibernate_final.ostapenko.hibernate.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllBySubTopicId(Long subTopicId, Pageable pageable);
}
