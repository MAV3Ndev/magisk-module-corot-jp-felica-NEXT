package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum JobStatus {
    CREATED("CREATED"),
    INITIALIZING("INITIALIZING"),
    PROCESSING("PROCESSING"),
    COMPLETING("COMPLETING"),
    COMPLETED("COMPLETED"),
    FAILING("FAILING"),
    FAILED("FAILED");

    private static final Map<String, JobStatus> enumMap;
    private String value;

    static {
        HashMap map = new HashMap();
        enumMap = map;
        map.put("CREATED", CREATED);
        enumMap.put("INITIALIZING", INITIALIZING);
        enumMap.put("PROCESSING", PROCESSING);
        enumMap.put("COMPLETING", COMPLETING);
        enumMap.put("COMPLETED", COMPLETED);
        enumMap.put("FAILING", FAILING);
        enumMap.put("FAILED", FAILED);
    }

    JobStatus(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static JobStatus fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
