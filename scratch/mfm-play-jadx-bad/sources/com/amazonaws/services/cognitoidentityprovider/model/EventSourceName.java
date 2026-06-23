package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum EventSourceName {
    UserNotification("userNotification");

    private static final Map<String, EventSourceName> enumMap;
    private String value;

    static {
        EventSourceName eventSourceName = UserNotification;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("userNotification", eventSourceName);
    }

    EventSourceName(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static EventSourceName fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, EventSourceName> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
