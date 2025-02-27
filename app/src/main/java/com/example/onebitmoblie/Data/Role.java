package com.example.onebitmoblie.Data;

public enum Role {
//    ADMIN,
//    NORMAL_USER;
    ADMIN(0),
    NORMAL_USER(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static Role fromString(String roleString) {
        if (roleString == null || roleString.isEmpty()) {
            return NORMAL_USER;
        }

        switch (roleString.trim().toUpperCase()) {
            case "ADMIN":
            case "0":
                return ADMIN;
            case "NORMAL_USER":
            case "1":
                return NORMAL_USER;
            default:
                throw new IllegalArgumentException("Invalid role: " + roleString);
        }
    }
}
