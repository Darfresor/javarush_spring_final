package com.javarush.hibernate_final.ostapenko.hibernate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "questions", schema = "myapp")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;
    @Column(length = 255)
    private String description;
}
