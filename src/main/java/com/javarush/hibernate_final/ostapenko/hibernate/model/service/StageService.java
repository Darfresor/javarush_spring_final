package com.javarush.hibernate_final.ostapenko.hibernate.model.service;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestTo;
import com.javarush.hibernate_final.ostapenko.hibernate.DTO.StageTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Stage;
import com.javarush.hibernate_final.ostapenko.hibernate.model.mapper.StageMapper;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StageService {
    private final StageRepository stageRepository;
    private final StageMapper stageMapper;

    @Autowired
    public StageService(StageRepository stageRepository, StageMapper stageMapper) {
        this.stageRepository = stageRepository;
        this.stageMapper = stageMapper;
    }

    public StageTo getRootStageOfQuest(Long id){
        Optional<Stage> stageOptional = stageRepository.findByIdAndIsQuestIdRootTrue(id);
        if(stageOptional.isPresent()){
            Stage stage = stageOptional.get();
            return stageMapper.toDto(stage);
        }else{
            throw new RuntimeException("Stage not found with id: " + id);
        }
    }

}
