package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum DeliveryStatus {
    SUCCESSFUL("SUCCESSFUL"),
    THROTTLED("THROTTLED"),
    TEMPORARY_FAILURE("TEMPORARY_FAILURE"),
    PERMANENT_FAILURE("PERMANENT_FAILURE"),
    UNKNOWN_FAILURE("UNKNOWN_FAILURE"),
    OPT_OUT("OPT_OUT"),
    DUPLICATE("DUPLICATE");

    private static final Map<String, DeliveryStatus> enumMap;
    private String value;

    static {
        HashMap map = new HashMap();
        enumMap = map;
        map.put("SUCCESSFUL", SUCCESSFUL);
        enumMap.put("THROTTLED", THROTTLED);
        enumMap.put("TEMPORARY_FAILURE", TEMPORARY_FAILURE);
        enumMap.put("PERMANENT_FAILURE", PERMANENT_FAILURE);
        enumMap.put("UNKNOWN_FAILURE", UNKNOWN_FAILURE);
        enumMap.put("OPT_OUT", OPT_OUT);
        enumMap.put("DUPLICATE", DUPLICATE);
    }

    DeliveryStatus(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static DeliveryStatus fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
