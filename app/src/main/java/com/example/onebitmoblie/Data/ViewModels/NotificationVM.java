package com.example.onebitmoblie.Data.ViewModels;

import com.example.onebitmoblie.Data.NotificationType;

import kotlin.text.UStringsKt;

public class NotificationVM {
    private String title;
    private String content;
    private NotificationType notificationType;
    private String createdAt;
    private String modifiedAt;
    private boolean isCreated;

    public NotificationVM(String title, String content, NotificationType notificationType, String createAt, String modifiedAt, boolean isCreated) {
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.createdAt = createAt;
        this.modifiedAt = modifiedAt;
        this.isCreated = isCreated;
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

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createAt) {
        this.createdAt = createAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }
}
