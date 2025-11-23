package com.javarush.hibernate_final.ostapenko.hibernate.model.service;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestService {
    private final QuestRepository questRepository;

    @Autowired
    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }
    public Page<Quest> getQuests(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return questRepository.findAll(pageable);
    }
}
