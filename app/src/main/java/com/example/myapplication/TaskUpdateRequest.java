package com.example.myapplication;

public class TaskUpdateRequest {

    private String title;
    private String dueDate;
    private String group;
    private String priority;

    public TaskUpdateRequest(String title, String dueDate, String group, String priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.group = group;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getGroup() {
        return group;
    }

    public String getPriority() {
        return priority;
    }
}
