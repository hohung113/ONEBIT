package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;

import java.util.UUID;

public class Notes extends BaseEntity {
    private String userId;
    private String activityId;
    private String title;
    private String content;
    private String fileName;

    public Notes(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy, String userId, String activityId, String title, String content, String fileName) {
        super(id, isDeleted, createdAt, modifiedAt, modifiedBy);
        this.userId = userId;
        this.activityId = activityId;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
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