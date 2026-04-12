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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
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


    private Quest createQuest(Long questId, String questName) {
        Quest quest = new Quest();
        quest.setId(questId);
        quest.setQuestName(questName);
        return quest;
    }

    private QuestTo createQuestTo(Long questId, String questName) {
        QuestTo questDto = new QuestTo();
        questDto.setId(questId);
        questDto.setQuestName(questName);
        return questDto;
    }

    @Test
    void getQuests_whenValidPageAndSize_thenReturnPageOfQuestTo() {
        int page = 0;
        int size = 5;
        Pageable expectedPageable = PageRequest.of(page, size);

        List<Quest> quests = List.of(
                createQuest(1L, "Квест1"),
                createQuest(2L, "Квест2")
        );

        Page<Quest> questPage = new PageImpl<>(quests, expectedPageable, 10);

        List<QuestTo> questDtos = List.of(
                createQuestTo(1L, "Квест1"),
                createQuestTo(2L, "Квест2")
        );

        when(questRepository.findAll(expectedPageable)).thenReturn(questPage);
        when(questMapper.toDtoList(quests)).thenReturn(questDtos);

        Page<QuestTo> result = questService.getQuests(page, size);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).isEqualTo(questDtos);
        assertThat(result.getNumber()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getTotalElements()).isEqualTo(10);

        verify(questRepository, times(1)).findAll(expectedPageable);
        verify(questMapper, times(1)).toDtoList(quests);


    }

    @Test
    void FindById_whenQuestExists_thenReturnQuestTo() {
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
        verify(questMapper, times(1)).toDto(quest);

    }


}
