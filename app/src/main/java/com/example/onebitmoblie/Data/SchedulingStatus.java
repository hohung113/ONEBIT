package com.example.onebitmoblie.Data;

public enum SchedulingStatus {
    COMPLETED(0),
    IN_PROGRESS(1);

    private final int value;

    SchedulingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SchedulingStatus fromInt(int value) {
        for (SchedulingStatus type : SchedulingStatus.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SchedulingStatus value: " + value);
    }
}
