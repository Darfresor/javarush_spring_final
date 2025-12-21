package com.javarush.hibernate_final.ostapenko.hibernate.DTO;

public interface TopicWithCountTo {
    Long getId();
    String getName();
    Long getSubtopicCount();
}
