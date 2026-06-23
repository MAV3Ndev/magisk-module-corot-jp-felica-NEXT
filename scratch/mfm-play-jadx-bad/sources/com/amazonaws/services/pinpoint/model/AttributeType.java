package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum AttributeType {
    INCLUSIVE("INCLUSIVE"),
    EXCLUSIVE("EXCLUSIVE");

    private static final Map<String, AttributeType> enumMap;
    private String value;

    static {
        AttributeType attributeType = INCLUSIVE;
        AttributeType attributeType2 = EXCLUSIVE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("INCLUSIVE", attributeType);
        map.put("EXCLUSIVE", attributeType2);
    }

    AttributeType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static AttributeType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, AttributeType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
