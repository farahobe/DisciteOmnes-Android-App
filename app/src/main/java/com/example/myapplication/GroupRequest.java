package com.example.myapplication;
public class GroupRequest {
    private String name;
    private String description;

    public GroupRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}