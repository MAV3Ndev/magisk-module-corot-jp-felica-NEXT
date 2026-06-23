package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum StatusType {
    Enabled("Enabled"),
    Disabled("Disabled");

    private static final Map<String, StatusType> enumMap;
    private String value;

    static {
        StatusType statusType = Enabled;
        StatusType statusType2 = Disabled;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("Enabled", statusType);
        map.put("Disabled", statusType2);
    }

    StatusType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static StatusType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, StatusType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
