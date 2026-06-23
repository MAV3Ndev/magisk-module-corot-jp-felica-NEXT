package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum Frequency {
    ONCE("ONCE"),
    HOURLY("HOURLY"),
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    EVENT("EVENT");

    private static final Map<String, Frequency> enumMap;
    private String value;

    static {
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ONCE", ONCE);
        enumMap.put("HOURLY", HOURLY);
        enumMap.put("DAILY", DAILY);
        enumMap.put("WEEKLY", WEEKLY);
        enumMap.put("MONTHLY", MONTHLY);
        enumMap.put("EVENT", EVENT);
    }

    Frequency(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static Frequency fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
