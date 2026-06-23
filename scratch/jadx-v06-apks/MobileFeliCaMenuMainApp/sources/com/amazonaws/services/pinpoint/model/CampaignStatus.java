package com.amazonaws.services.pinpoint.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum CampaignStatus {
    SCHEDULED("SCHEDULED"),
    EXECUTING("EXECUTING"),
    PENDING_NEXT_RUN("PENDING_NEXT_RUN"),
    COMPLETED("COMPLETED"),
    PAUSED("PAUSED"),
    DELETED("DELETED");

    private static final Map<String, CampaignStatus> enumMap;
    private String value;

    static {
        HashMap map = new HashMap();
        enumMap = map;
        map.put("SCHEDULED", SCHEDULED);
        enumMap.put("EXECUTING", EXECUTING);
        enumMap.put("PENDING_NEXT_RUN", PENDING_NEXT_RUN);
        enumMap.put("COMPLETED", COMPLETED);
        enumMap.put("PAUSED", PAUSED);
        enumMap.put("DELETED", DELETED);
    }

    CampaignStatus(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static CampaignStatus fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
