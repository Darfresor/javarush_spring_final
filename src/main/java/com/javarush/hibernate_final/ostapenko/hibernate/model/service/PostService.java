package com.javarush.hibernate_final.ostapenko.hibernate.model.service;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.TopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Post;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository repository) {
        this.repository = repository;
    }
    public Page<Post> getPosts(Long subTopicId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Post> postPage = repository.findAllBySubTopicId(subTopicId, pageable);
        List<Post> topicList = postPage.getContent();
        return new PageImpl<>(
                topicList,
                pageable,
                postPage.getTotalElements()
        );
    }


}
