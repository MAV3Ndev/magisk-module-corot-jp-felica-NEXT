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
        DeliveryStatus deliveryStatus = SUCCESSFUL;
        DeliveryStatus deliveryStatus2 = THROTTLED;
        DeliveryStatus deliveryStatus3 = TEMPORARY_FAILURE;
        DeliveryStatus deliveryStatus4 = PERMANENT_FAILURE;
        DeliveryStatus deliveryStatus5 = UNKNOWN_FAILURE;
        DeliveryStatus deliveryStatus6 = OPT_OUT;
        DeliveryStatus deliveryStatus7 = DUPLICATE;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("SUCCESSFUL", deliveryStatus);
        map.put("THROTTLED", deliveryStatus2);
        map.put("TEMPORARY_FAILURE", deliveryStatus3);
        map.put("PERMANENT_FAILURE", deliveryStatus4);
        map.put("UNKNOWN_FAILURE", deliveryStatus5);
        map.put("OPT_OUT", deliveryStatus6);
        map.put("DUPLICATE", deliveryStatus7);
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
        Map<String, DeliveryStatus> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
