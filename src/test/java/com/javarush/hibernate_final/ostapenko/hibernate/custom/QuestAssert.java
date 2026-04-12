package com.javarush.hibernate_final.ostapenko.hibernate.custom;

import com.javarush.hibernate_final.ostapenko.hibernate.entity.Quest;
import org.assertj.core.api.AbstractAssert;

import java.util.Optional;

public class QuestAssert extends AbstractAssert<QuestAssert, Optional<Quest>> {
    public QuestAssert(Optional<Quest> quest) {
        super(quest, QuestAssert.class);
    }

    public static QuestAssert assertThat(Optional<Quest> actual){
        return new QuestAssert(actual);
    }

    // Проверка, что Optional не пустой
    public QuestAssert isPresent() {
        isNotNull();
        if (!actual.isPresent()) {
            failWithMessage("Expected Optional to be present, but it was empty");
        }
        return this;
    }

    public QuestAssert hasId(Long expectedId) {
        isNotNull();
        if (!actual.isPresent()) {
            failWithMessage("Expected Optional to be present, but it was empty");
        }

        Long actualId = actual.get().getId();
        if (!actualId.equals(expectedId)) {
            failWithMessage("Expected ID to be <%d> but was <%d>", expectedId, actualId);
        }
        return this;
    }

    public QuestAssert hasName(String expectedName) {
        isNotNull();
        if (!actual.isPresent()) {
            failWithMessage("Expected Optional to be present, but it was empty");
        }

        String actualName = actual.get().getQuestName();
        if (!actualName.equals(expectedName)) {
            failWithMessage("Expected name to be <%s> but was <%s>", expectedName, actualName);
        }
        return this;
    }
}
