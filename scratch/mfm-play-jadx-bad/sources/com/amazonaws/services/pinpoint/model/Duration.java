package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum Duration {
    HR_24("HR_24"),
    DAY_7("DAY_7"),
    DAY_14("DAY_14"),
    DAY_30("DAY_30");

    private static final Map<String, Duration> enumMap;
    private String value;

    static {
        Duration duration = HR_24;
        Duration duration2 = DAY_7;
        Duration duration3 = DAY_14;
        Duration duration4 = DAY_30;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("HR_24", duration);
        map.put("DAY_7", duration2);
        map.put("DAY_14", duration3);
        map.put("DAY_30", duration4);
    }

    Duration(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static Duration fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, Duration> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
