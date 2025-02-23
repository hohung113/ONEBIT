package com.example.onebitmoblie.common;

public class ConvertEnumValue {
    public static <T extends Enum<T>> String getEnumStringFromDB(Class<T> enumClass, int dbValue) {
        T[] constants = enumClass.getEnumConstants();
        if (dbValue >= 0 && dbValue < constants.length) {
            return constants[dbValue].name();
        }
        return "UNKNOWN";
    }
}
// Example
//int dbValue = 1;
//String statusString = getEnumStringFromDB(SchedulingStatus.class, dbValue);