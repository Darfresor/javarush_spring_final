package com.javarush.hibernate_final.ostapenko.hibernate.DTO;

public interface SubTopicWithCountTo {
    Long getId();
    String getName();
    Long getPostsCount();
    Long getTopicId();
}
