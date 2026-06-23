package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;

/* JADX INFO: loaded from: classes.dex */
public enum JobStatus {
    CREATED(DebugCoroutineInfoImplKt.CREATED),
    INITIALIZING("INITIALIZING"),
    PROCESSING("PROCESSING"),
    COMPLETING("COMPLETING"),
    COMPLETED("COMPLETED"),
    FAILING("FAILING"),
    FAILED("FAILED");

    private static final Map<String, JobStatus> enumMap;
    private String value;

    static {
        JobStatus jobStatus = CREATED;
        JobStatus jobStatus2 = INITIALIZING;
        JobStatus jobStatus3 = PROCESSING;
        JobStatus jobStatus4 = COMPLETING;
        JobStatus jobStatus5 = COMPLETED;
        JobStatus jobStatus6 = FAILING;
        JobStatus jobStatus7 = FAILED;
        HashMap map = new HashMap();
        enumMap = map;
        map.put(DebugCoroutineInfoImplKt.CREATED, jobStatus);
        map.put("INITIALIZING", jobStatus2);
        map.put("PROCESSING", jobStatus3);
        map.put("COMPLETING", jobStatus4);
        map.put("COMPLETED", jobStatus5);
        map.put("FAILING", jobStatus6);
        map.put("FAILED", jobStatus7);
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
        Map<String, JobStatus> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
