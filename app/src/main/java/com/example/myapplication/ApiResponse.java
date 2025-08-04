package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;
}