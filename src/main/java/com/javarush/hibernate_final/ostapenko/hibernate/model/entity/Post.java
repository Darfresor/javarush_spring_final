package com.javarush.hibernate_final.ostapenko.hibernate.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "posts", schema = "myapp")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sub_topic_id")
    private SubTopic subTopic;
    private String content;
}
