package com.javarush.hibernate_final.ostapenko.hibernate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "quests", schema = "myapp")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quest_name", length = 100)
    private String questName;
    @Column(length = 8_000)
    private String description;
    @Column(name="img_path")
    private String imgPath;
    @Column(name="is_new")
    private boolean isNew;


    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stage> stageList;

    public Quest(Long id, String questName, String description) {
        this.id = id;
        this.questName = questName;
        this.description = description;
    }
}
