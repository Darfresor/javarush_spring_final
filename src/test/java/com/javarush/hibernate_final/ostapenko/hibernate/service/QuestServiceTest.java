package com.javarush.hibernate_final.ostapenko.hibernate.service;


import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestTo;
import com.javarush.hibernate_final.ostapenko.hibernate.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.mapper.QuestMapper;
import com.javarush.hibernate_final.ostapenko.hibernate.repository.QuestRepository;
import jakarta.persistence.Column;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestServiceTest {
    @Mock
    private QuestRepository questRepository;
    @Mock
    private QuestMapper questMapper;

    @InjectMocks
    private QuestService questService;

    @Test
    void givenFindById_whenQuestExists_thenReturnQuestTo() {
        Long questId = 1L;
        String questName = "Квест-тест";
        String questDescription = "описание-тест";
        String questImgPath = "пусто";
        boolean questIsNew = false;

        Quest quest = new Quest();
        quest.setId(questId);
        quest.setQuestName(questName);
        quest.setDescription(questDescription);
        quest.setImgPath(questImgPath);
        quest.setNew(questIsNew);

        QuestTo expectedDto = new QuestTo();
        expectedDto.setId(questId);
        expectedDto.setQuestName(questName);
        expectedDto.setDescription(questDescription);
        expectedDto.setImgPath(questImgPath);
        expectedDto.setNew(questIsNew);

        when(questRepository.findById(questId)).thenReturn(Optional.of(quest));
        when(questMapper.toDto(quest)).thenReturn(expectedDto);

        QuestTo result = questService.findById(questId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(questId);
        assertThat(result.getQuestName()).isEqualTo(questName);
        assertThat(result.getDescription()).isEqualTo(questDescription);
        assertThat(result.getImgPath()).isEqualTo(questImgPath);
        assertThat(result.isNew()).isEqualTo(questIsNew);

        verify(questRepository, times(1)).findById(questId);
        verify(questMapper,times(1)).toDto(quest);

    }


}
