package com.javarush.hibernate_final.ostapenko.hibernate.DTO.workshop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkshopQuestDTO {
    private Long id;
    private String questName;
    private String description;
    private boolean isNew;
    private Integer stageCount;
}