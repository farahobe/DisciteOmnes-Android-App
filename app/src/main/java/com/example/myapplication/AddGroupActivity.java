package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGroupActivity extends AppCompatActivity {

    private EditText editTextName, editTextDesc;
    private Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        editTextName = findViewById(R.id.editTextGroupName);
        editTextDesc = findViewById(R.id.editTextGroupDesc);
        buttonCreate = findViewById(R.id.buttonCreateGroup);

        buttonCreate.setOnClickListener(v -> createGroup());
    }

    private void createGroup() {
        String name = editTextName.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Bitte Gruppenname eingeben", Toast.LENGTH_SHORT).show();
            return;
        }

        GroupRequest request = new GroupRequest(name, desc);
        DisciteOmnesApi api = RetrofitClient.getInstance().getApi();

        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token == null) {
            Toast.makeText(this, "Nicht eingeloggt", Toast.LENGTH_SHORT).show();
            return;
        }

        api.createGroup("Bearer " + token, request).enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddGroupActivity.this, "Gruppe erstellt!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddGroupActivity.this, "Erstellung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Toast.makeText(AddGroupActivity.this, "Fehler: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
