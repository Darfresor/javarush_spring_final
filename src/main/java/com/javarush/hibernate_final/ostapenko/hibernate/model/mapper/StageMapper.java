package com.javarush.hibernate_final.ostapenko.hibernate.model.mapper;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.StageTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Stage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {QuestMapper.class, QuestionMapper.class}
)
public interface StageMapper {
    StageTo toDto(Stage stage);

    List<StageTo> toDtoList(List<Stage> stageList);

    Stage toEntity(StageTo stageTo);
}
