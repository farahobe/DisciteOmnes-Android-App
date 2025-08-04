package com.example.myapplication;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Group {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;


    @SerializedName("members")
    public List<String> members;
}