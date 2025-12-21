package com.javarush.hibernate_final.ostapenko.hibernate.model.service;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.TopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Topic;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    public Page<Topic> getTopics(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Topic> topicPage = topicRepository.findAll(pageable);
        List<Topic> topicList = topicPage.getContent();
        return new PageImpl<>(
                topicList,
                pageable,
                topicPage.getTotalElements()
        );
    }

    public Page<TopicWithCountTo> getTopicsWithCount(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<TopicWithCountTo> topicPage = topicRepository.findAllTopicsWithCount(pageable);
        List<TopicWithCountTo> topicList = topicPage.getContent();
        return new PageImpl<>(
                topicList,
                pageable,
                topicPage.getTotalElements()
        );
    }



}
