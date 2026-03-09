package com.lubolabao.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskCompletedListener listener;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public interface OnTaskCompletedListener {
        void onTaskCompleted(Task task, int position);
    }

    public TaskAdapter(List<Task> taskList, OnTaskCompletedListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvDifficulty.setText("Difficulty: " + task.getDifficulty());

        if (task.getDeadlineTime() != null) {
            holder.tvDeadline.setVisibility(View.VISIBLE);
            holder.tvDeadline.setText("Deadline: " + timeFormat.format(new Date(task.getDeadlineTime())));
        } else {
            holder.tvDeadline.setVisibility(View.GONE);
        }

        holder.btnComplete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskCompleted(task, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDifficulty, tvDeadline;
        MaterialButton btnComplete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
            btnComplete = itemView.findViewById(R.id.btnComplete);
        }
    }
}
