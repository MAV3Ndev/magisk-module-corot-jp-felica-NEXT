package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum LogLevel {
    ERROR("ERROR");

    private static final Map<String, LogLevel> enumMap;
    private String value;

    static {
        LogLevel logLevel = ERROR;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ERROR", logLevel);
    }

    LogLevel(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static LogLevel fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, LogLevel> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
