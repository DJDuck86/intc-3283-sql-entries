package edu.northwestu.intc3283.datasourcestarter.tasks.entity;

import jakarta.validation.constraints.Size;

public class TaskRequest {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Size(min = 5)
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
