package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum Action {
    OPEN_APP("OPEN_APP"),
    DEEP_LINK("DEEP_LINK"),
    URL("URL");

    private static final Map<String, Action> enumMap;
    private String value;

    static {
        HashMap map = new HashMap();
        enumMap = map;
        map.put("OPEN_APP", OPEN_APP);
        enumMap.put("DEEP_LINK", DEEP_LINK);
        enumMap.put("URL", URL);
    }

    Action(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static Action fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
