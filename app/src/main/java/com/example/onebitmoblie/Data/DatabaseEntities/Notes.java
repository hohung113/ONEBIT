package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;

import java.util.UUID;

public class Notes extends BaseEntity {
    private UUID userId;
    private UUID activityId;
    private String title;
    private String content;
    private String fileName;

    public Notes() {
    }

    public Notes(UUID userId, UUID activityId, String title, String content, String fileName) {
        this.userId = userId;
        this.activityId = activityId;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}