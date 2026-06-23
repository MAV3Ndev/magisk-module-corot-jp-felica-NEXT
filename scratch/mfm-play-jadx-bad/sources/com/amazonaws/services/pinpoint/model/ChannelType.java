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
        ChannelType channelType = GCM;
        ChannelType channelType2 = APNS;
        ChannelType channelType3 = APNS_SANDBOX;
        ChannelType channelType4 = APNS_VOIP;
        ChannelType channelType5 = APNS_VOIP_SANDBOX;
        ChannelType channelType6 = ADM;
        ChannelType channelType7 = SMS;
        ChannelType channelType8 = VOICE;
        ChannelType channelType9 = EMAIL;
        ChannelType channelType10 = BAIDU;
        ChannelType channelType11 = CUSTOM;
        HashMap map = new HashMap();
        enumMap = map;
        map.put(CodePackage.GCM, channelType);
        map.put("APNS", channelType2);
        map.put("APNS_SANDBOX", channelType3);
        map.put("APNS_VOIP", channelType4);
        map.put("APNS_VOIP_SANDBOX", channelType5);
        map.put("ADM", channelType6);
        map.put("SMS", channelType7);
        map.put("VOICE", channelType8);
        map.put("EMAIL", channelType9);
        map.put("BAIDU", channelType10);
        map.put("CUSTOM", channelType11);
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
        Map<String, ChannelType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
