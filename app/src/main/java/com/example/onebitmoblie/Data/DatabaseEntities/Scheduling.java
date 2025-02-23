package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Scheduling extends BaseEntity {
    private String title;
    private UUID userId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String description;
    private SchedulingStatus status;

    public Scheduling() {
    }

    public Scheduling(String title, UUID userId, LocalDateTime fromDate, LocalDateTime toDate, String description, SchedulingStatus status) {
        this.title = title;
        this.userId = userId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }
}