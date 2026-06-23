package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum SegmentType {
    DIMENSIONAL("DIMENSIONAL"),
    IMPORT("IMPORT");

    private static final Map<String, SegmentType> enumMap;
    private String value;

    static {
        SegmentType segmentType = DIMENSIONAL;
        SegmentType segmentType2 = IMPORT;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("DIMENSIONAL", segmentType);
        map.put("IMPORT", segmentType2);
    }

    SegmentType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static SegmentType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, SegmentType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
