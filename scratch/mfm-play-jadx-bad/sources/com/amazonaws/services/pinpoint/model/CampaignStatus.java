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
        CampaignStatus campaignStatus = SCHEDULED;
        CampaignStatus campaignStatus2 = EXECUTING;
        CampaignStatus campaignStatus3 = PENDING_NEXT_RUN;
        CampaignStatus campaignStatus4 = COMPLETED;
        CampaignStatus campaignStatus5 = PAUSED;
        CampaignStatus campaignStatus6 = DELETED;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("SCHEDULED", campaignStatus);
        map.put("EXECUTING", campaignStatus2);
        map.put("PENDING_NEXT_RUN", campaignStatus3);
        map.put("COMPLETED", campaignStatus4);
        map.put("PAUSED", campaignStatus5);
        map.put("DELETED", campaignStatus6);
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
        Map<String, CampaignStatus> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
