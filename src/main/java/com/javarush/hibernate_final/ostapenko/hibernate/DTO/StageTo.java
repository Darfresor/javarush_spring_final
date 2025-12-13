package com.javarush.hibernate_final.ostapenko.hibernate.DTO;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class StageTo {
    private Long id;
    private boolean isQuestIdRoot;
    private Quest quest;
    private String title;
    private String description;
    private String imgPath;
}
