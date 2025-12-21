package com.javarush.hibernate_final.ostapenko.hibernate.model.service;


import com.javarush.hibernate_final.ostapenko.hibernate.DTO.TopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.SubTopic;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.SubTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubTopicService {
    private final SubTopicRepository repository;

    @Autowired
    public SubTopicService(SubTopicRepository repository) {
        this.repository = repository;
    }
    public Page<SubTopic> getSubTopics(Long topicId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<SubTopic> subTopicPage = repository.findAllByTopicId(topicId, pageable);
        List<SubTopic> subTopicList = subTopicPage.getContent();
        return new PageImpl<>(
                subTopicList,
                pageable,
                subTopicPage.getTotalElements()
        );
    }
}
