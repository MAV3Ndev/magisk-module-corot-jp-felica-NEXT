package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum Mode {
    DELIVERY("DELIVERY"),
    FILTER("FILTER");

    private static final Map<String, Mode> enumMap;
    private String value;

    static {
        Mode mode = DELIVERY;
        Mode mode2 = FILTER;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("DELIVERY", mode);
        map.put("FILTER", mode2);
    }

    Mode(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static Mode fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, Mode> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
