package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum Format {
    CSV("CSV"),
    JSON("JSON");

    private static final Map<String, Format> enumMap;
    private String value;

    static {
        Format format = CSV;
        Format format2 = JSON;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("CSV", format);
        map.put("JSON", format2);
    }

    Format(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static Format fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, Format> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
