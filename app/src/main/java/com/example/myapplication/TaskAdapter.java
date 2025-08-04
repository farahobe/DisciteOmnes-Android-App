package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task_tracker, parent, false); // Layout-Name beachten!
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.title.setText(task.getTitle());
        holder.dueDate.setText("📅 Due: " + task.getDueDate());
        holder.group.setText(task.getGroup());

        // Emoji passend zur Priorität
        switch (task.getPriority()) {
            case "high":
                holder.emoji.setText("🔴");
                break;
            case "medium":
                holder.emoji.setText("🟠");
                break;
            case "low":
                holder.emoji.setText("🟢");
                break;
            default:
                holder.emoji.setText("⚪");
                break;
        }

        // Farbindikator links passend zur Priorität
        View indicator = holder.itemView.findViewById(R.id.priority_indicator);
        switch (task.getPriority()) {
            case "high":
                indicator.setBackgroundColor(Color.parseColor("#E53935")); // Rot
                break;
            case "medium":
                indicator.setBackgroundColor(Color.parseColor("#FB8C00")); // Orange
                break;
            case "low":
                indicator.setBackgroundColor(Color.parseColor("#43A047")); // Grün
                break;
            default:
                indicator.setBackgroundColor(Color.LTGRAY); // Neutral
                break;
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView emoji, title, dueDate, group;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            emoji = itemView.findViewById(R.id.task_checkbox_emoji);
            title = itemView.findViewById(R.id.task_title_text_view);
            dueDate = itemView.findViewById(R.id.task_due_date_text_view);
            group = itemView.findViewById(R.id.task_group_text_view);
        }
    }
}
