package com.amazonaws.services.pinpoint.model;

import com.google.android.gms.stats.CodePackage;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum ChannelType {
    GCM(CodePackage.GCM),
    APNS("APNS"),
    APNS_SANDBOX("APNS_SANDBOX"),
    APNS_VOIP("APNS_VOIP"),
    APNS_VOIP_SANDBOX("APNS_VOIP_SANDBOX"),
    ADM("ADM"),
    SMS("SMS"),
    VOICE("VOICE"),
    EMAIL("EMAIL"),
    BAIDU("BAIDU"),
    CUSTOM("CUSTOM");

    private static final Map<String, ChannelType> enumMap;
    private String value;

    static {
        HashMap map = new HashMap();
        enumMap = map;
        map.put(CodePackage.GCM, GCM);
        enumMap.put("APNS", APNS);
        enumMap.put("APNS_SANDBOX", APNS_SANDBOX);
        enumMap.put("APNS_VOIP", APNS_VOIP);
        enumMap.put("APNS_VOIP_SANDBOX", APNS_VOIP_SANDBOX);
        enumMap.put("ADM", ADM);
        enumMap.put("SMS", SMS);
        enumMap.put("VOICE", VOICE);
        enumMap.put("EMAIL", EMAIL);
        enumMap.put("BAIDU", BAIDU);
        enumMap.put("CUSTOM", CUSTOM);
    }

    ChannelType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static ChannelType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
