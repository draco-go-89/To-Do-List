package com.lubolabao.todolist;

public class Task {
    private String title;
    private String difficulty;
    private Long deadlineTime;
    private Integer reminderMinutesBefore;

    public Task(String title, String difficulty) {
        this.title = title;
        this.difficulty = difficulty;
    }

    public Task(String title, String difficulty, Long deadlineTime, Integer reminderMinutesBefore) {
        this.title = title;
        this.difficulty = difficulty;
        this.deadlineTime = deadlineTime;
        this.reminderMinutesBefore = reminderMinutesBefore;
    }

    public String getTitle() { return title; }
    public String getDifficulty() { return difficulty; }
    public Long getDeadlineTime() { return deadlineTime; }
    public void setDeadlineTime(Long deadlineTime) { this.deadlineTime = deadlineTime; }
    public Integer getReminderMinutesBefore() { return reminderMinutesBefore; }
    public void setReminderMinutesBefore(Integer reminderMinutesBefore) { this.reminderMinutesBefore = reminderMinutesBefore; }
}
