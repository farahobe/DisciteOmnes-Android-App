package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class TaskRequest {


    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("groupId")
    private String groupId;

    @SerializedName("isCompleted")
    private String isCompleted;


    public TaskRequest(String title, String description, String groupId, String isCompleted) {
        this.title = title;
        this.description = description;
        this.groupId = groupId;
        this.isCompleted = isCompleted;
    }


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public String getIsCompleted() { return isCompleted; }
    public void setIsCompleted(String isCompleted) { this.isCompleted = isCompleted; }
}