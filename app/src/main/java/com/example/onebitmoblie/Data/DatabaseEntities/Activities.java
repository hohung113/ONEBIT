package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.ActivityType;
import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.Piority;

import java.time.LocalDateTime;
import java.util.UUID;

public class Activities {
    private String id;
    private String title;
    private String imageUrl;
    private ActivityType type;
    private Piority piority;
    private boolean isDeleted;
    private String createdAt;
    private String modifiedAt;
    private String modifiedBy;

    public Activities() {
    }

    public Activities(String id, String title, String imageUrl, ActivityType type, Piority piority, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.type = type;
        this.piority = piority;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Piority getPiority() {
        return piority;
    }

    public void setPiority(Piority piority) {
        this.piority = piority;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
