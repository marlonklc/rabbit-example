package com.asyncexample.api.response;

import com.asyncexample.dto.Step;

public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private Step step;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Step getStep() {
        return step;
    }
}
