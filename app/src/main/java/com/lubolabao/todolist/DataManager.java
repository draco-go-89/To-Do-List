package com.lubolabao.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String PREF_NAME = "LegendTaskerPrefs";
    private static final String KEY_GOLD = "current_gold";
    private static final String KEY_UNLOCKED_SKINS = "unlocked_skins";
    private static final String KEY_EQUIPPED_SKIN = "equipped_skin_id";
    private static final String KEY_WALLPAPER = "selected_wallpaper_id";
    private static final String KEY_COMPLETED_TASKS = "completed_tasks";

    private SharedPreferences prefs;

    public DataManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public int getCurrentGold() {
        return prefs.getInt(KEY_GOLD, 0);
    }

    public void saveGold(int gold) {
        prefs.edit().putInt(KEY_GOLD, gold).apply();
    }

    public List<Integer> getUnlockedSkinIds() {
        Set<String> skinSet = prefs.getStringSet(KEY_UNLOCKED_SKINS, new HashSet<>());
        List<Integer> unlockedIds = new ArrayList<>();
        for (String s : skinSet) {
            unlockedIds.add(Integer.parseInt(s));
        }
        return unlockedIds;
    }

    public void addUnlockedSkin(int skinId) {
        Set<String> skinSet = new HashSet<>(prefs.getStringSet(KEY_UNLOCKED_SKINS, new HashSet<>()));
        skinSet.add(String.valueOf(skinId));
        prefs.edit().putStringSet(KEY_UNLOCKED_SKINS, skinSet).apply();
    }

    public int getEquippedSkinId() {
        return prefs.getInt(KEY_EQUIPPED_SKIN, -1);
    }

    public void setEquippedSkinId(int skinId) {
        prefs.edit().putInt(KEY_EQUIPPED_SKIN, skinId).apply();
    }

    public int getSelectedWallpaperId() {
        return prefs.getInt(KEY_WALLPAPER, R.drawable.bg_nature_1);
    }

    public void setSelectedWallpaperId(int wallpaperId) {
        prefs.edit().putInt(KEY_WALLPAPER, wallpaperId).apply();
    }

    public void addCompletedTask(Task task) {
        Set<String> completedSet = new HashSet<>(prefs.getStringSet(KEY_COMPLETED_TASKS, new HashSet<>()));
        completedSet.add(task.getTitle() + "|" + task.getDifficulty());
        prefs.edit().putStringSet(KEY_COMPLETED_TASKS, completedSet).apply();
    }

    public List<Task> getCompletedTasks() {
        Set<String> completedSet = prefs.getStringSet(KEY_COMPLETED_TASKS, new HashSet<>());
        List<Task> completedTasks = new ArrayList<>();
        for (String s : completedSet) {
            String[] parts = s.split("\\|");
            if (parts.length == 2) {
                completedTasks.add(new Task(parts[0], parts[1]));
            }
        }
        return completedTasks;
    }

    public void onTaskCompleted(String difficulty) {
        int reward = 0;
        switch (difficulty) {
            case "Easy": reward = 10; break;
            case "Medium": reward = 25; break;
            case "Hard": reward = 50; break;
        }
        saveGold(getCurrentGold() + reward);
    }
}
