package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = "AddTaskActivity";

    private EditText editTextTitle, editTextDescription, editTextGroupId;
    private Button buttonCreateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_task);

        // Die IDs müssen mit denen in deiner XML-Datei übereinstimmen.
        editTextTitle = findViewById(R.id.editTextTaskTitle);
        editTextDescription = findViewById(R.id.editTextTaskDescription);
        editTextGroupId = findViewById(R.id.editTextTaskGroupId); // Du hattest hier editTextTaskGroupId
        buttonCreateTask = findViewById(R.id.buttonCreateTask);

        buttonCreateTask.setOnClickListener(v -> createTask());
    }

    private void createTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String groupId = editTextGroupId.getText().toString().trim();

        // Überprüfung, ob die notwendigen Felder ausgefüllt sind
        if (title.isEmpty() || groupId.isEmpty()) {
            Toast.makeText(this, "Bitte Titel und Gruppen-ID eingeben.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Token aus den SharedPreferences abrufen
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token == null) {
            Toast.makeText(this, "Fehler: Sie sind nicht angemeldet.", Toast.LENGTH_SHORT).show();

            // startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        // Erstelle das korrekte Anfrage-Objekt mit vier Parametern
        TaskRequest request = new TaskRequest(title, description, groupId, "false");

        // Hole die API über den neuen RetrofitClient
        DisciteOmnesApi api = RetrofitClient.getInstance().getApi();

        // Führe den API-Aufruf durch
        api.addTask("Bearer " + token, groupId, request).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddTaskActivity.this, "Aufgabe erfolgreich erstellt!", Toast.LENGTH_SHORT).show();
                    finish(); // Schließe die Activity und kehre zur vorherigen zurück
                } else {

                    String errorMsg = "Fehler beim Erstellen der Aufgabe. Code: " + response.code();
                    Log.e(TAG, errorMsg); // Logge den Fehler für Debugging
                    Toast.makeText(AddTaskActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                // Fehler bei der Netzwerkverbindung
                String failureMsg = "Netzwerkfehler: " + t.getMessage();
                Log.e(TAG, failureMsg, t); // Logge den Stack Trace
                Toast.makeText(AddTaskActivity.this, failureMsg, Toast.LENGTH_LONG).show();
            }
        });
    }
}