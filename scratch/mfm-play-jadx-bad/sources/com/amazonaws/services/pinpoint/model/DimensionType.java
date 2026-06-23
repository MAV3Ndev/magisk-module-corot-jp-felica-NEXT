package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum DimensionType {
    INCLUSIVE("INCLUSIVE"),
    EXCLUSIVE("EXCLUSIVE");

    private static final Map<String, DimensionType> enumMap;
    private String value;

    static {
        DimensionType dimensionType = INCLUSIVE;
        DimensionType dimensionType2 = EXCLUSIVE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("INCLUSIVE", dimensionType);
        map.put("EXCLUSIVE", dimensionType2);
    }

    DimensionType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static DimensionType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, DimensionType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
