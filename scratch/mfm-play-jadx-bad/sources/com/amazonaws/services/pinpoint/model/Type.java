package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum Type {
    ALL("ALL"),
    ANY("ANY"),
    NONE("NONE");

    private static final Map<String, Type> enumMap;
    private String value;

    static {
        Type type = ALL;
        Type type2 = ANY;
        Type type3 = NONE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ALL", type);
        map.put("ANY", type2);
        map.put("NONE", type3);
    }

    Type(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static Type fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, Type> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
