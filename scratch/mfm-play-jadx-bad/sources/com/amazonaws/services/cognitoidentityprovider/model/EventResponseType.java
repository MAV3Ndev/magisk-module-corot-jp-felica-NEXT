package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum EventResponseType {
    Pass("Pass"),
    Fail("Fail"),
    InProgress("InProgress");

    private static final Map<String, EventResponseType> enumMap;
    private String value;

    static {
        EventResponseType eventResponseType = Pass;
        EventResponseType eventResponseType2 = Fail;
        EventResponseType eventResponseType3 = InProgress;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("Pass", eventResponseType);
        map.put("Fail", eventResponseType2);
        map.put("InProgress", eventResponseType3);
    }

    EventResponseType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static EventResponseType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, EventResponseType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
