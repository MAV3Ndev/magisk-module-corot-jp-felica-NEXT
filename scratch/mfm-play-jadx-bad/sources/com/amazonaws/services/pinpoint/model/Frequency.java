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
        Frequency frequency = ONCE;
        Frequency frequency2 = HOURLY;
        Frequency frequency3 = DAILY;
        Frequency frequency4 = WEEKLY;
        Frequency frequency5 = MONTHLY;
        Frequency frequency6 = EVENT;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ONCE", frequency);
        map.put("HOURLY", frequency2);
        map.put("DAILY", frequency3);
        map.put("WEEKLY", frequency4);
        map.put("MONTHLY", frequency5);
        map.put("EVENT", frequency6);
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
        Map<String, Frequency> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
