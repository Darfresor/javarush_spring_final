package com.javarush.hibernate_final.ostapenko.hibernate.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AnswerTo {
    private Long id;
    private Long stageId;
    private String description;
}
