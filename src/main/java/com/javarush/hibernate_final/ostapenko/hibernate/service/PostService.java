package com.javarush.hibernate_final.ostapenko.hibernate.service;

import com.javarush.hibernate_final.ostapenko.hibernate.entity.Post;
import com.javarush.hibernate_final.ostapenko.hibernate.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(noRollbackFor = RuntimeException.class)
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
