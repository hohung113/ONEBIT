package com.example.onebitmoblie.Data.DatabaseEntities;
import com.example.onebitmoblie.Data.BaseEntity;
import java.util.UUID;

public class NotificationLines extends BaseEntity {
    public String notificationId;
    public String schedulingId;
    public String toUserId;
    public Boolean isRead;

    public NotificationLines(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy, String notificationId, String schedulingId, String toUserId, Boolean isRead) {
        super(id, isDeleted, createdAt, modifiedAt, modifiedBy);
        this.notificationId = notificationId;
        this.schedulingId = schedulingId;
        this.toUserId = toUserId;
        this.isRead = isRead;
    }
    public NotificationLines(){
        super();

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

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}