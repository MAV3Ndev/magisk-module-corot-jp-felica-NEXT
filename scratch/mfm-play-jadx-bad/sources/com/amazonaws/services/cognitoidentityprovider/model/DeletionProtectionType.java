package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum DeletionProtectionType {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private static final Map<String, DeletionProtectionType> enumMap;
    private String value;

    static {
        DeletionProtectionType deletionProtectionType = ACTIVE;
        DeletionProtectionType deletionProtectionType2 = INACTIVE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("ACTIVE", deletionProtectionType);
        map.put("INACTIVE", deletionProtectionType2);
    }

    DeletionProtectionType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static DeletionProtectionType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, DeletionProtectionType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
