package com.asyncexample.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    private String id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Step step = Step.first();

    public Task() {}

    public Task(String id, String title, String description, Step step) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.step = step;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Step getStep() {
        return step;
    }

    public Step nextStep() {
        this.step = step.next();
        return step;
    }

    @Override
    public String toString() {
        return "{\"id\" : " + (id == null ? null : "\"" + id + "\"") +
                ",\"title\" : " + (title == null ? null : "\"" + title + "\"") +
                ",\"description\" : " + (description == null ? null : "\"" + description + "\"") +
                ",\"step\" : " + (step == null ? null : "\"" + step.name() + "\"") +
                "}";
    }
}
