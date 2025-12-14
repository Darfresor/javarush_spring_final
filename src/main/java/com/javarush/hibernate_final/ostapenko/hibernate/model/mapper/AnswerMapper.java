package com.javarush.hibernate_final.ostapenko.hibernate.model.mapper;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.AnswerTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(source = "stage.id",target = "stageId")
    AnswerTo toDto(Answer answer);

    List<AnswerTo> toDtoList(List<Answer> answerList);
}
