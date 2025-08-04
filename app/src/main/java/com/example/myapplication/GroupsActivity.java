package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        recyclerView = findViewById(R.id.recyclerViewGroups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        DisciteOmnesApi api = RetrofitClient.getInstance().getApi();
        api.getGroups("Bearer " + token).enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new GroupAdapter(GroupsActivity.this, response.body(), userId);
                    recyclerView = findViewById(R.id.groups_recycler_view);

                } else {
                    Toast.makeText(GroupsActivity.this, "Fehler beim Laden", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Toast.makeText(GroupsActivity.this, "Netzwerkfehler: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
