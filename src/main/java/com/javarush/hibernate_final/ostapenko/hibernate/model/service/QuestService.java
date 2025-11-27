package com.javarush.hibernate_final.ostapenko.hibernate.model.service;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.model.mapper.QuestMapper;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestService {
    private final QuestRepository questRepository;
    private final QuestMapper questMapper;

    @Autowired
    public QuestService(QuestRepository questRepository, QuestMapper questMapper) {
        this.questRepository = questRepository;
        this.questMapper = questMapper;
    }
    public Page<QuestTo> getQuests(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Quest> questPage = questRepository.findAll(pageable);
        List<QuestTo> questDtos = questMapper.toDtoList(questPage.getContent());

        return new PageImpl<>(
                questDtos,
                pageable,
                questPage.getTotalElements()
        );
    }
    public QuestTo findById(Long id){
        Optional<Quest> questOptional = questRepository.findById(id);
        if(questOptional.isPresent()){
            Quest quest = questOptional.get();
            return questMapper.toDto(quest);
        }else{
            throw new RuntimeException("Quest not found with id: " + id);
        }
    }
}
