package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Scheduling extends BaseEntity {
    private String title;
    private String userId;
    private String fromDate;
    private String toDate;
    private String description;
    private SchedulingStatus status;

    public Scheduling(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy, String title, String userId, String fromDate, String toDate, String description, SchedulingStatus status) {
        super(id, isDeleted, createdAt, modifiedAt, modifiedBy);
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
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