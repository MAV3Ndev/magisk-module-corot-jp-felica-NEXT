package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum TimeUnitsType {
    Seconds("seconds"),
    Minutes("minutes"),
    Hours("hours"),
    Days("days");

    private static final Map<String, TimeUnitsType> enumMap;
    private String value;

    static {
        TimeUnitsType timeUnitsType = Seconds;
        TimeUnitsType timeUnitsType2 = Minutes;
        TimeUnitsType timeUnitsType3 = Hours;
        TimeUnitsType timeUnitsType4 = Days;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("seconds", timeUnitsType);
        map.put("minutes", timeUnitsType2);
        map.put("hours", timeUnitsType3);
        map.put("days", timeUnitsType4);
    }

    TimeUnitsType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static TimeUnitsType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, TimeUnitsType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
