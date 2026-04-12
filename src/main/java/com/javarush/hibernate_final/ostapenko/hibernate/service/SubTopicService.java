package com.javarush.hibernate_final.ostapenko.hibernate.service;


import com.javarush.hibernate_final.ostapenko.hibernate.DTO.SubTopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.repository.SubTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(noRollbackFor = RuntimeException.class)
public class SubTopicService {
    private final SubTopicRepository repository;

    @Autowired
    public SubTopicService(SubTopicRepository repository) {
        this.repository = repository;
    }
    public Page<SubTopicWithCountTo> getSubTopics(Long topicId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<SubTopicWithCountTo> subTopicPage = repository.findAllByTopicIdWithCount(topicId, pageable);
        List<SubTopicWithCountTo> subTopicList = subTopicPage.getContent();
        return new PageImpl<>(
                subTopicList,
                pageable,
                subTopicPage.getTotalElements()
        );
    }
}
