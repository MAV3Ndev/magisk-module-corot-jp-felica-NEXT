package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum RecencyType {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private static final Map<String, RecencyType> enumMap;
    private String value;

    static {
        RecencyType recencyType = ACTIVE;
        RecencyType recencyType2 = INACTIVE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ACTIVE", recencyType);
        map.put("INACTIVE", recencyType2);
    }

    RecencyType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static RecencyType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, RecencyType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
