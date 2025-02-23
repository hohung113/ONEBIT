package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.ActivityType;
import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.Piority;

public class Activities extends BaseEntity {
    private String title;
    private String imageUrl;
    private ActivityType type;
    private Piority piority;

    public Activities() {
    }

    public Activities(String title, String imageUrl, ActivityType type, Piority piority) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.type = type;
        this.piority = piority;
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
}
