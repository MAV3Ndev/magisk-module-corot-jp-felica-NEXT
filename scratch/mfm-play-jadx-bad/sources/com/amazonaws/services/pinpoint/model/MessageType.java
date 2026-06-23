package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum MessageType {
    TRANSACTIONAL("TRANSACTIONAL"),
    PROMOTIONAL("PROMOTIONAL");

    private static final Map<String, MessageType> enumMap;
    private String value;

    static {
        MessageType messageType = TRANSACTIONAL;
        MessageType messageType2 = PROMOTIONAL;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("TRANSACTIONAL", messageType);
        map.put("PROMOTIONAL", messageType2);
    }

    MessageType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static MessageType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, MessageType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
