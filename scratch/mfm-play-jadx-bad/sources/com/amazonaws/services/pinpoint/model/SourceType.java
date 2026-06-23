package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum SourceType {
    ALL("ALL"),
    ANY("ANY"),
    NONE("NONE");

    private static final Map<String, SourceType> enumMap;
    private String value;

    static {
        SourceType sourceType = ALL;
        SourceType sourceType2 = ANY;
        SourceType sourceType3 = NONE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ALL", sourceType);
        map.put("ANY", sourceType2);
        map.put("NONE", sourceType3);
    }

    SourceType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static SourceType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, SourceType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
