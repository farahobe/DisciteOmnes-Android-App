package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DisciteOmnesApi {

    // Login für JSON-Server
    @GET("users")
    Call<List<User>> loginJsonServer(
            @Query("email") String email,
            @Query("password") String password
    );

    // Registrierung
    @POST("users")
    Call<User> registerUser(@Body User user);

    // Gruppen abrufen (mit Token)
    @GET("groups")
    Call<List<Group>> getGroups(@Header("Authorization") String token);

    // Neue Gruppe erstellen
    @POST("groups")
    Call<Group> createGroup(@Header("Authorization") String token, @Body GroupRequest request);

    // Gruppe löschen
    @DELETE("groups/{id}")
    Call<Void> deleteGroup(@Path("id") String groupId);

    // Gruppe beitreten
    @POST("groups/{id}/join")
    Call<ApiResponse> joinGroup(@Header("Authorization") String token, @Path("id") String groupId);

    // Alle Aufgaben abrufen
    @GET("tasks")
    Call<List<Task>> getAllTasks();

    @POST("groups/{groupId}/tasks")
    Call<Task> addTask(
            @Header("Authorization") String token,
            @Path("groupId") String groupId,
            @Body TaskRequest taskRequest
    );

    // Aufgabe aktualisieren
    @PUT("tasks/{taskId}")
    Call<Task> updateTask(@Path("taskId") int taskId, @Body TaskUpdateRequest updateRequest);

}