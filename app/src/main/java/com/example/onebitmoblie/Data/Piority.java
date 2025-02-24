package com.example.onebitmoblie.Data;

public enum Piority {
    HIGH(0),
    MEDIUM(1),
    LOW(2);

    private final int value;

    Piority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Piority fromString(String piorityString) {
        if (piorityString == null || piorityString.isEmpty()) {
            return MEDIUM;
        }

        switch (piorityString.trim().toUpperCase()) {
            case "HIGH":
            case "0":
                return HIGH;
            case "MEDIUM":
            case "1":
                return MEDIUM;
            case "LOW":
            case "2":
                return LOW;
            default:
                throw new IllegalArgumentException("Invalid Piority: " + piorityString);
        }
    }
}

