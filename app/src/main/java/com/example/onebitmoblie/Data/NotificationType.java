package com.example.onebitmoblie.Data;

public enum NotificationType {

    SCHEDULES(0),
    SYSTEM(1);

    private final int value;

    NotificationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NotificationType fromInt(int value) {
        for (NotificationType type : NotificationType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid NotificationType value: " + value);
    }


}
