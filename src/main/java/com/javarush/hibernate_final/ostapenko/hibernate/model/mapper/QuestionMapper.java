package com.javarush.hibernate_final.ostapenko.hibernate.model.mapper;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestionTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionTo toDto(Question question);
    Question toEntity(QuestionTo questionTo);
}
