package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskTrackerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private DisciteOmnesApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_tracker);

        recyclerView = findViewById(R.id.task_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        api = RetrofitClient.getInstance().getApi();

        loadTasks();
    }

    private void loadTasks() {
        api.getAllTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new TaskAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(TaskTrackerActivity.this, "Fehler beim Laden: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Toast.makeText(TaskTrackerActivity.this, "Netzwerkfehler: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
