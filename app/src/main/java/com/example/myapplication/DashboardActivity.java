package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    private MaterialCardView groupsCard, tasksCard, progressCard;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerViewGroups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groupsCard = findViewById(R.id.groups_card);
        tasksCard = findViewById(R.id.tasks_card);
        progressCard = findViewById(R.id.progress_card);
        welcomeText = findViewById(R.id.welcome_text);

        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String name = prefs.getString("userName", "");
        welcomeText.setText("Hallo " + name + " 👋");

        groupsCard.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, GroupsActivity.class)));

        tasksCard.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, TaskListActivity.class)));

        progressCard.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, StudienplanActivity.class)));

        loadGroups();
    }

    private void loadGroups() {
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        String userId = prefs.getString("userId", null);

        if (token == null || userId == null) {
            Toast.makeText(this, "Nicht eingeloggt", Toast.LENGTH_SHORT).show();
            return;
        }

        DisciteOmnesApi apiService = RetrofitClient.getInstance().getApi();
        Call<List<Group>> call = apiService.getGroups("Bearer " + token);

        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Group> groups = response.body();
                    adapter = new GroupAdapter(DashboardActivity.this, groups, userId);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(DashboardActivity.this, "Fehler beim Laden der Gruppen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Netzwerkfehler: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
