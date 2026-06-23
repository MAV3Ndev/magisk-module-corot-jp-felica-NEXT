package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum FilterType {
    SYSTEM("SYSTEM"),
    ENDPOINT("ENDPOINT");

    private static final Map<String, FilterType> enumMap;
    private String value;

    static {
        HashMap map = new HashMap();
        enumMap = map;
        map.put("SYSTEM", SYSTEM);
        enumMap.put("ENDPOINT", ENDPOINT);
    }

    FilterType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static FilterType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
