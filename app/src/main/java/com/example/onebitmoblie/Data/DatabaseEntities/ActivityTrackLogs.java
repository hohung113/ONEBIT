package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ActivityTrackLogs extends BaseEntity {
    private String activityId;
    private String schedulingId;
    private String startTime;
    private String endTime;
    private boolean status;

    public ActivityTrackLogs(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy, String activityId, String schedulingId, String startTime, String endTime, boolean status) {
        super(id, isDeleted, createdAt, modifiedAt, modifiedBy);
        this.activityId = activityId;
        this.schedulingId = schedulingId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(String schedulingId) {
        this.schedulingId = schedulingId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}