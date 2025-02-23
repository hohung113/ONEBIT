package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.NotificationType;

public class Notifications extends BaseEntity {
    private String title;
    private String content;
    private NotificationType type;

    public Notifications() {
    }

    public Notifications(String title, String content, NotificationType type) {
        this.title = title;
        this.content = content;
        this.type = type;
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

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}