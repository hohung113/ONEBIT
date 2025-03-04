package com.example.onebitmoblie.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntity {
    public String id;
    public boolean isDeleted;
    public String createdAt;
    public String modifiedAt;
    public String modifiedBy;

    public BaseEntity(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy) {
        this.id = id;
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
