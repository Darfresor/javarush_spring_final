package com.javarush.hibernate_final.ostapenko.hibernate.service;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestTo;
import com.javarush.hibernate_final.ostapenko.hibernate.aspect.annotaion.EnableLogging;
import com.javarush.hibernate_final.ostapenko.hibernate.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.mapper.QuestMapper;
import com.javarush.hibernate_final.ostapenko.hibernate.repository.QuestRepository;
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
import java.util.Optional;
/*
* isolation:
*  DEFAULT- уровень самой базы данных
* READ_UNCOMMITED- чтение данных которые еще не зафиксированы другими
* READ_COMMITED- устранено грязное чтение
* REPEATABLE_READ - устранено грязное чтение и неповторяющееся чтение
* SERIALIZABLE - устранено грязное, неповторяющееся и фантомное чтение
* timeout: - время отведенное на транзакцию( по умолчанию -1, то есть время без ограничений)
* Propagation:
* REQUIRED - целевой метод не может работать без активной транзакции(использует текущую или создает новой если ее нет)
* REQUIRES_NEW - всегда создавать новую транзакцию(если хотим изолировать транзакцию и ее результаты от родительской если есть)
* MANDATORY - требуется активная транзакция, если ее нет - будет исключение.
* SUPPORTS  - целевой метод может выполнять и без транзакции
* NOT_SUPPORTED - целевой метод не требует транзакции, если она есть, она будет приостановлена
* NEVER - вызовет исключение если выполнение происходит в транзакции
* NESTED - создает вложенную транзакцию
* */

@Service
@Transactional(noRollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED, timeout = 100, propagation = Propagation.REQUIRED)
public class QuestService {
    private final QuestRepository questRepository;
    private final QuestMapper questMapper;

    @Autowired
    public QuestService(QuestRepository questRepository, QuestMapper questMapper) {
        this.questRepository = questRepository;
        this.questMapper = questMapper;
    }
    public Optional<Quest> getQuestByIdWithStage(long questId){
        return questRepository.findByIdWithStages(questId);
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
