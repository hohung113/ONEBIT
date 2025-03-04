package com.example.onebitmoblie.Data.ViewModels;

import com.example.onebitmoblie.Data.NotificationType;

import kotlin.text.UStringsKt;

public class NotificationVM {
    private String notificationId;
    private String schedulingId;
    private String title;
    private String content;
    private NotificationType notificationType;
    private String createdAt;
    private String modifiedAt;
    private boolean isCreated;//isREAd

    public NotificationVM(String notificationId, String schedulingId, String title, String content, NotificationType notificationType, String createdAt, String modifiedAt, boolean isCreated) {
        this.notificationId = notificationId;
        this.schedulingId = schedulingId;
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isCreated = isCreated;
    }
    public NotificationVM(){

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

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(String schedulingId) {
        this.schedulingId = schedulingId;
    }
}
