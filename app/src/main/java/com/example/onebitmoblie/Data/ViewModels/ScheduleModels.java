package com.example.onebitmoblie.Data.ViewModels;

public class ScheduleModels {
    private String title;
    private String date;
    private int progress;
    public ScheduleModels(String title, String date, int progress) {
        this.title = title;
        this.date = date;
        this.progress = progress;
    }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public int getProgress() { return progress; }
}
