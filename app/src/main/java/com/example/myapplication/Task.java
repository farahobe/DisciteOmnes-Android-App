package com.example.myapplication;

public class Task {

    private int id;
    private String title;
    private String dueDate;
    private String group;
    private String priority;

    public Task(String title, String dueDate, String group, String priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.group = group;
        this.priority = priority;
    }

    public int getId() {
        return id;
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
