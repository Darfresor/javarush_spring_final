package com.javarush.hibernate_final.ostapenko.hibernate.DTO;


import jakarta.persistence.Column;
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
    private boolean win;
    private boolean defeat;
    private QuestTo quest;
    private String title;
    private String description;
    private String imgPath;
    private QuestionTo question;
}
