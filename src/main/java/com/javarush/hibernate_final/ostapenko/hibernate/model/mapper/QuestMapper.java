package com.javarush.hibernate_final.ostapenko.hibernate.model.mapper;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestMapper {

    QuestTo toDto(Quest quest);

    Quest toEntity(QuestTo questTo);

    List<QuestTo> toDtoList(List<Quest> quests);
}
