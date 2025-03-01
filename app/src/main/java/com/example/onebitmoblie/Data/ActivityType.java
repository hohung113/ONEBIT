package com.example.onebitmoblie.Data;

public enum ActivityType {
    LEARNING(0),
    WORKING(1),
    EXERCISING(2),
    RELAXING(3),
    TRAVELING(4),
    SOCIALIZING(5),
    SHOPPING(6),
    ENTERTAINMENT(7),
    VOLUNTEERING(8),
    OTHER(9);

    private final int value;

    ActivityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ActivityType fromString(String activityTypeString) {
        if (activityTypeString == null || activityTypeString.isEmpty()) {
            return OTHER;
        }

        switch (activityTypeString.trim().toUpperCase()) {
            case "LEARNING":
            case "0":
                return LEARNING;
            case "WORKING":
            case "1":
                return WORKING;
            case "EXERCISING":
            case "2":
                return EXERCISING;
            case "RELAXING":
            case "3":
                return RELAXING;
            case "TRAVELING":
            case "4":
                return TRAVELING;
            case "SOCIALIZING":
            case "5":
                return SOCIALIZING;
            case "SHOPPING":
            case "6":
                return SHOPPING;
            case "ENTERTAINMENT":
            case "7":
                return ENTERTAINMENT;
            case "VOLUNTEERING":
            case "8":
                return VOLUNTEERING;
            case "OTHER":
            case "9":
                return OTHER;
            default:
                throw new IllegalArgumentException("Invalid ActivityType: " + activityTypeString);
        }
    }
}
