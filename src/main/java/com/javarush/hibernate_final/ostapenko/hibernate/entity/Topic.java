package com.javarush.hibernate_final.ostapenko.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="topics", schema = "myapp")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTopic> subTopic;
}
