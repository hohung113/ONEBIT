package com.example.onebitmoblie.Data.DatabaseEntities;
import com.example.onebitmoblie.Data.BaseEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public class Statistics extends BaseEntity {
    private String userId;
    private String startTimeToReport;
    private String endTimeToReport;
    private String contentReport;

    public Statistics(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy, String userId, String startTimeToReport, String endTimeToReport, String contentReport) {
        super(id, isDeleted, createdAt, modifiedAt, modifiedBy);
        this.userId = userId;
        this.startTimeToReport = startTimeToReport;
        this.endTimeToReport = endTimeToReport;
        this.contentReport = contentReport;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTimeToReport() {
        return startTimeToReport;
    }

    public void setStartTimeToReport(String startTimeToReport) {
        this.startTimeToReport = startTimeToReport;
    }

    public String getEndTimeToReport() {
        return endTimeToReport;
    }

    public void setEndTimeToReport(String endTimeToReport) {
        this.endTimeToReport = endTimeToReport;
    }

    public String getContentReport() {
        return contentReport;
    }

    public void setContentReport(String contentReport) {
        this.contentReport = contentReport;
    }
}