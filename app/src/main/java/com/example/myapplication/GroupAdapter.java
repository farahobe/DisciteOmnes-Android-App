package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private final Context context;
    private final List<Group> groupList;
    private final String currentUserId;

    public GroupAdapter(Context context, List<Group> groupList, String currentUserId) {
        this.context = context;
        this.groupList = groupList;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This links to your XML file. Ensure the file is named 'list_item_group.xml'.
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.nameTextView.setText(group.name);
        holder.descriptionTextView.setText(group.description);

        // Color rotation logic
        int[] colors = { 0xFFE57373, 0xFF64B5F6, 0xFF81C784, 0xFFFFD54F, 0xFFBA68C8 };
        holder.sideColorView.setBackgroundColor(colors[position % colors.length]);

        // Logic for the Join button
        if (group.members != null && group.members.contains(currentUserId)) {
            holder.joinButton.setVisibility(View.GONE);
        } else {
            holder.joinButton.setVisibility(View.VISIBLE);
            holder.joinButton.setOnClickListener(v -> joinGroup(group, position));
        }

        // Logic for the Delete button
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Gruppe löschen")
                    .setMessage("Möchtest du diese Gruppe wirklich löschen?")
                    .setPositiveButton("Ja", (dialog, which) -> deleteGroup(group.id, position))
                    .setNegativeButton("Abbrechen", null)
                    .show();
        });
    }

    private void joinGroup(Group group, int position) {
        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            Toast.makeText(context, "Nicht eingeloggt", Toast.LENGTH_SHORT).show();
            return;
        }

        DisciteOmnesApi api = RetrofitClient.getInstance().getApi();
        api.joinGroup("Bearer " + token, group.id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Toast.makeText(context, "Erfolgreich beigetreten!", Toast.LENGTH_SHORT).show();
                    if (group.members != null) {
                        group.members.add(currentUserId);
                    }
                    notifyItemChanged(position); // Refresh this item in the list
                } else {
                    Toast.makeText(context, "Beitritt fehlgeschlagen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, "Netzwerkfehler: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteGroup(String groupId, int position) {

        DisciteOmnesApi api = RetrofitClient.getInstance().getApi();
        api.deleteGroup(groupId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    groupList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, groupList.size()); // Update subsequent items
                    Toast.makeText(context, "Gruppe gelöscht", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Löschen fehlgeschlagen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Netzwerkfehler: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView;
        View sideColorView;
        ImageView deleteButton;
        Button joinButton;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_group_name);
            descriptionTextView = itemView.findViewById(R.id.text_group_description);
            sideColorView = itemView.findViewById(R.id.sideColorView);
            deleteButton = itemView.findViewById(R.id.buttonDeleteGroup);
            joinButton = itemView.findViewById(R.id.buttonJoinGroup);
        }
    }
}