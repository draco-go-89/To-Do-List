package com.lubolabao.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DataManager dataManager;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private TextView tvGoldBalance;
    private DrawerLayout drawerLayout;
    private ImageView mainBackground;
    
    private Long selectedDeadline = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new DataManager(this);
        taskList = new ArrayList<>();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainBackground = findViewById(R.id.mainBackground);
        updateBackground();

        tvGoldBalance = findViewById(R.id.tvGoldBalance);
        updateGoldBalance();

        RecyclerView rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new TaskAdapter(taskList, (task, position) -> {
            dataManager.onTaskCompleted(task.getDifficulty());
            dataManager.addCompletedTask(task); // Save to history
            updateGoldBalance();
            taskList.remove(position);
            adapter.notifyItemRemoved(position);
        });
        rvTasks.setAdapter(adapter);

        findViewById(R.id.toolbar).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(v -> {
            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(100).withEndAction(() -> 
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).setInterpolator(new AccelerateDecelerateInterpolator()).start()
            ).start();
            showAddTaskDialog();
        });

        findViewById(R.id.btnOpenWardrobe).setOnClickListener(v -> {
            startActivity(new Intent(this, WardrobeActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGoldBalance();
        updateBackground();
    }

    private void updateBackground() {
        mainBackground.setImageResource(dataManager.getSelectedWallpaperId());
    }

    private void updateGoldBalance() {
        tvGoldBalance.setText(dataManager.getCurrentGold() + "g");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_settings) {
            showWallpaperDialog();
        } else if (id == R.id.nav_about) {
            showAboutDialog();
        } else if (id == R.id.nav_contact) {
            openInstagram();
        } else if (id == R.id.nav_completed) {
            showCompletedTasksDialog();
        } else if (id == R.id.nav_me) {
            showMeDialog();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showWallpaperDialog() {
        String[] colors = {"Forest Green", "Sunset Orange", "Ocean Blue", "Mountain Grey", "Autumn Red"};
        int[] drawables = {R.drawable.bg_nature_1, R.drawable.bg_nature_2, R.drawable.bg_nature_3, R.drawable.bg_nature_4, R.drawable.bg_nature_5};
        
        new AlertDialog.Builder(this, R.style.GlassDialogTheme)
            .setTitle("Select Wallpaper")
            .setItems(colors, (dialog, which) -> {
                dataManager.setSelectedWallpaperId(drawables[which]);
                updateBackground();
                Toast.makeText(this, colors[which] + " applied!", Toast.LENGTH_SHORT).show();
            })
            .show();
    }

    private void showCompletedTasksDialog() {
        List<Task> completed = dataManager.getCompletedTasks();
        StringBuilder message = new StringBuilder();
        
        if (completed.isEmpty()) {
            message.append("No completed tasks yet!");
        } else {
            for (Task t : completed) {
                message.append("✔ ").append(t.getTitle()).append(" (").append(t.getDifficulty()).append(")\n");
            }
        }

        new AlertDialog.Builder(this, R.style.GlassDialogTheme)
            .setTitle("Completed Tasks")
            .setMessage(message.toString())
            .setPositiveButton("Close", null)
            .show();
    }

    private void showAboutDialog() {
        String content = "1. Getting Started\n" +
                "When you open Legend-Tasker, you’ll see your Quest Board (the main screen).\n" +
                "• The Gold Balance: Located at the top, shows your current wealth.\n" +
                "• The Background: Your current quest environment (customizable in settings).\n" +
                "• Navigation Drawer: Access Settings, History, and Info from the top-left menu.\n\n" +
                "2. Adding Your Missions\n" +
                "1. Tap the (+) button.\n" +
                "2. Task Title: Enter what you need to achieve.\n" +
                "3. Difficulty: Easy (10g), Medium (25g), Hard (50g).\n" +
                "4. Set Deadline: Pick a time for your quest.\n" +
                "5. Set Reminder: Choose notification offset (5, 10, 15, or 30 mins).\n" +
                "6. Tap Add to begin.\n\n" +
                "3. Completing Quests\n" +
                "• Mark tasks complete to immediately receive Gold Coins.\n" +
                "• Completed tasks are moved to your Quest History in the sidebar.\n\n" +
                "4. Hero Wardrobe\n" +
                "• Spend gold to unlock Common, Rare, Epic, and Legend skins.\n" +
                "• Equip owned skins to update your hero's look.\n\n" +
                "5. Customizing Your World\n" +
                "• Change the scenery via Sidebar -> Change Wallpaper.\n\n" +
                "6. Reminders\n" +
                "• Allow 'Exact Alarms' permission for precise reminders.\n\n" +
                "7. Contact the Creator\n" +
                "• Select 'Me' for developer info or 'Contact Me' for Instagram.";

        new AlertDialog.Builder(this, R.style.GlassDialogTheme)
            .setTitle("Legend-Tasker Guide")
            .setMessage(content)
            .setPositiveButton("Got it!", null)
            .show();
    }

    private void showMeDialog() {
        new AlertDialog.Builder(this, R.style.GlassDialogTheme)
            .setTitle("Me")
            .setMessage("Hello I'm Dhrubo Basumatary, I'm a Software Engineer & App Developer. I build high-perfomance apps and solve real-world problems through vibe-coding. Feel free to contact me through Email - (lubolabao509@gmail.com) or Follow me in my Instagram (Follow the link to my Ig through Contact me Menu). Thank You!")
            .setPositiveButton("OK", null)
            .show();
    }

    private void openInstagram() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/___lubolabao.1___/"));
        startActivity(intent);
    }

    private void showAddTaskDialog() {
        selectedDeadline = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.GlassDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        builder.setView(view);

        TextInputEditText etTaskTitle = view.findViewById(R.id.etTaskTitle);
        ChipGroup cgDifficulty = view.findViewById(R.id.cgDifficulty);
        ChipGroup cgReminder = view.findViewById(R.id.cgReminder);
        Button btnSetDeadline = view.findViewById(R.id.btnSetDeadline);

        btnSetDeadline.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(this, (timePicker, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                selectedDeadline = calendar.getTimeInMillis();
                btnSetDeadline.setText(String.format(Locale.getDefault(), "Deadline: %02d:%02d", hourOfDay, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = etTaskTitle.getText().toString();
            if (!title.isEmpty()) {
                int checkedDifficultyId = cgDifficulty.getCheckedChipId();
                String difficulty = checkedDifficultyId == R.id.chipMedium ? "Medium" : (checkedDifficultyId == R.id.chipHard ? "Hard" : "Easy");
                
                Integer reminder = null;
                int checkedReminderId = cgReminder.getCheckedChipId();
                if (checkedReminderId == R.id.chip5min) reminder = 5;
                else if (checkedReminderId == R.id.chip10min) reminder = 10;
                else if (checkedReminderId == R.id.chip15min) reminder = 15;
                else if (checkedReminderId == R.id.chip30min) reminder = 30;

                Task newTask = new Task(title, difficulty, selectedDeadline, reminder);
                taskList.add(newTask);
                adapter.notifyItemInserted(taskList.size() - 1);
                
                if (selectedDeadline != null && reminder != null) {
                    scheduleAlarm(newTask);
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private void scheduleAlarm(Task task) {
        if (task.getDeadlineTime() == null || task.getReminderMinutesBefore() == null) return;

        long reminderTime = task.getDeadlineTime() - (task.getReminderMinutesBefore() * 60 * 1000);
        
        if (reminderTime <= System.currentTimeMillis()) {
            return; // Don't schedule for past time
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TaskReminderReceiver.class);
        intent.putExtra("task_title", task.getTitle());
        
        int requestCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 
                requestCode, 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
            } else {
                Intent permissionIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(permissionIntent);
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
        }
    }
}
