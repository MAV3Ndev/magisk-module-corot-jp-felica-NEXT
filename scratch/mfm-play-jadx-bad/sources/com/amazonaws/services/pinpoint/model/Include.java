package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum Include {
    ALL("ALL"),
    ANY("ANY"),
    NONE("NONE");

    private static final Map<String, Include> enumMap;
    private String value;

    static {
        Include include = ALL;
        Include include2 = ANY;
        Include include3 = NONE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ALL", include);
        map.put("ANY", include2);
        map.put("NONE", include3);
    }

    Include(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static Include fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, Include> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
