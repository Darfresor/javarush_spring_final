package com.javarush.hibernate_final.ostapenko.hibernate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "stages", schema = "myapp")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"quest"})
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_quest_root")
    private boolean isQuestIdRoot;
    @ManyToOne
    @JoinColumn(name = "quests_id")
    private Quest quest;
    @Column(length = 255)
    private String title;
    @Column(length = 8_000)
    private String description;
    @Column(name = "img_path", length = 255)
    private String imgPath;
    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
