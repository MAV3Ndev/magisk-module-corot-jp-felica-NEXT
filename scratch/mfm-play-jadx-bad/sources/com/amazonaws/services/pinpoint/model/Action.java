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
        Action action = OPEN_APP;
        Action action2 = DEEP_LINK;
        Action action3 = URL;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("OPEN_APP", action);
        map.put("DEEP_LINK", action2);
        map.put("URL", action3);
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
        Map<String, Action> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
