package com.javarush.hibernate_final.ostapenko.hibernate.mapper;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestionTo;
import com.javarush.hibernate_final.ostapenko.hibernate.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {AnswerMapper.class}
)
public interface QuestionMapper {
    @Mapping(source = "answerList", target = "answerList")
    QuestionTo toDto(Question question);
    Question toEntity(QuestionTo questionTo);
}
