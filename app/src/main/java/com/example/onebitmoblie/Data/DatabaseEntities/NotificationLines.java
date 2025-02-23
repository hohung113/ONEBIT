package com.example.onebitmoblie.Data.DatabaseEntities;
import com.example.onebitmoblie.Data.BaseEntity;
import java.util.UUID;

public class NotificationLines extends BaseEntity {
    private UUID notificationId;
    private UUID schedulingId;
    private UUID toUserId;
    private Boolean isRead;

    public NotificationLines() {
    }

    public NotificationLines(UUID notificationId, UUID schedulingId, UUID toUserId, Boolean isRead) {
        this.notificationId = notificationId;
        this.schedulingId = schedulingId;
        this.toUserId = toUserId;
        this.isRead = isRead;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
    }

    public UUID getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(UUID schedulingId) {
        this.schedulingId = schedulingId;
    }

    public UUID getToUserId() {
        return toUserId;
    }

    public void setToUserId(UUID toUserId) {
        this.toUserId = toUserId;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}