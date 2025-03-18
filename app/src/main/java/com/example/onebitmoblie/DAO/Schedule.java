package com.example.onebitmoblie.DAO;

public class Schedule {
    private String title;
    private String date;
    private int progress;
    public Schedule(String title, String date, int progress) {
        this.title = title;
        this.date = date;
        this.progress = progress;
    }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public int getProgress() { return progress; }
}
