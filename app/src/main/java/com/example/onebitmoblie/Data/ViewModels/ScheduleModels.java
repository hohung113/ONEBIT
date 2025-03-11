package com.example.onebitmoblie.Data.ViewModels;

public class ScheduleModels {
    public String id;
    public String title;
    public String date;
    public int progress;
    public String userId;
    public boolean isDeleted;

    public ScheduleModels() {

    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public int getProgress() { return 50; }
    public String getUserId() { return userId; }
    public boolean isDeleted() { return isDeleted; }
}