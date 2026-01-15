package com.javarush.hibernate_final.ostapenko.hibernate.model.service.workshop;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.workshop.WorkshopQuestDTO;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.model.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkshopQuestService {

    private final QuestRepository questRepository;


    @Transactional(readOnly = true)
    public List<WorkshopQuestDTO> getAllQuests() {
        List<Quest> quests = questRepository.findAll();

        return quests.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private WorkshopQuestDTO convertToDTO(Quest quest) {
        return new WorkshopQuestDTO(
                quest.getId(),
                quest.getQuestName(),
                quest.getDescription() != null && quest.getDescription().length() > 50
                        ? quest.getDescription().substring(0, 50) + "..."
                        : quest.getDescription(),
                quest.isNew(),
                quest.getStageList() != null ? quest.getStageList().size() : 0
        );
    }
}
