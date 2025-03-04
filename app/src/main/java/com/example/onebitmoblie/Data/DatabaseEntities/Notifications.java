package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.NotificationType;

import java.util.UUID;

public class Notifications extends BaseEntity {
    private String title;
    private String content;
    private NotificationType type;

    public Notifications(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy, String title, String content, NotificationType type) {
        super(id, isDeleted, createdAt, modifiedAt, modifiedBy);
        this.title = title;
        this.content = content;
        this.type = type;
    }
    public Notifications() {
        super(null, false, null, null, null);  // Gọi constructor của class cha
        this.title = "";
        this.content = "";
        this.type = NotificationType.SYSTEM; // Hoặc giá trị mặc định phù hợp
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