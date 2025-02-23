package com.example.onebitmoblie.Data.DatabaseEntities;
import com.example.onebitmoblie.Data.BaseEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public class Statistics extends BaseEntity {
    private UUID userId;
    private LocalDateTime startTimeToReport;
    private LocalDateTime endTimeToReport;
    private String contentReport;

    public Statistics() {
    }

    public Statistics(UUID userId, LocalDateTime startTimeToReport, LocalDateTime endTimeToReport, String contentReport) {
        this.userId = userId;
        this.startTimeToReport = startTimeToReport;
        this.endTimeToReport = endTimeToReport;
        this.contentReport = contentReport;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTimeToReport() {
        return startTimeToReport;
    }

    public void setStartTimeToReport(LocalDateTime startTimeToReport) {
        this.startTimeToReport = startTimeToReport;
    }

    public LocalDateTime getEndTimeToReport() {
        return endTimeToReport;
    }

    public void setEndTimeToReport(LocalDateTime endTimeToReport) {
        this.endTimeToReport = endTimeToReport;
    }

    public String getContentReport() {
        return contentReport;
    }

    public void setContentReport(String contentReport) {
        this.contentReport = contentReport;
    }
}