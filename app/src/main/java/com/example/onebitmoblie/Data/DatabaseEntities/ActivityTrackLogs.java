package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ActivityTrackLogs extends BaseEntity {
    private UUID activityId;
    private UUID schedulingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean status;

    public ActivityTrackLogs() {
    }

    public ActivityTrackLogs(UUID activityId, UUID schedulingId, LocalDateTime startTime, LocalDateTime endTime, boolean status) {
        this.activityId = activityId;
        this.schedulingId = schedulingId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }

    public UUID getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(UUID schedulingId) {
        this.schedulingId = schedulingId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}