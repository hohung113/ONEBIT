package com.example.onebitmoblie.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.UUID;

public class ActivityDTOs implements Parcelable {
    private UUID scheduleID;
    private UUID activityID;
    private String title;
    private String startTime;
    private String endTime;

    public ActivityDTOs(UUID scheduleID, UUID activityID, String title, String startTime, String endTime) {
        this.scheduleID = scheduleID;
        this.activityID = activityID;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected ActivityDTOs(Parcel in)
    {
        scheduleID = UUID.fromString(in.readString());
        activityID = UUID.fromString(in.readString());
        title = in.readString();
        startTime = in.readString();
        endTime = in.readString();
    }

    public static final Creator<ActivityDTOs> CREATOR = new Creator<ActivityDTOs>() {
        @Override
        public ActivityDTOs createFromParcel(Parcel parcel) {
            return new ActivityDTOs(parcel);

        }
        @Override
        public ActivityDTOs[] newArray(int size) {
            return new ActivityDTOs[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(scheduleID.toString());
        parcel.writeString(activityID.toString());
        parcel.writeString(title);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public UUID getActivityID() {
        return activityID;
    }

    public void setActivityID(UUID activityID) {
        this.activityID = activityID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public UUID getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(UUID scheduleID) {
        this.scheduleID = scheduleID;
    }
}
