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
        HashMap map = new HashMap();
        enumMap = map;
        map.put("HR_24", HR_24);
        enumMap.put("DAY_7", DAY_7);
        enumMap.put("DAY_14", DAY_14);
        enumMap.put("DAY_30", DAY_30);
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
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
