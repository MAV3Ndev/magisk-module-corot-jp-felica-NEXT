package com.felicanetworks.semc.util;

import com.felicanetworks.semc.FlavorConst;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class SettingInfo {
    private static String sServerConnectionUrl = "sem-device.fnopf.jp";
    private static Map<String, String> sServerPublicKeysForLinkageData = FlavorConst.DEFAULT_SERVER_PUBLIC_KEYS_FOR_LINKAGE_DATA;
    private static Map<String, String> sServerPublicKeysForClientConfig = FlavorConst.DEFAULT_SERVER_PUBLIC_KEYS_FOR_CLIENT_CONFIG;

    private SettingInfo() {
    }

    public static void setProfileValue(String str, Map<String, String> map, Map<String, String> map2) {
        LogMgr.log(6, "000");
        sServerConnectionUrl = str;
        sServerPublicKeysForLinkageData = map;
        sServerPublicKeysForClientConfig = map2;
        LogMgr.log(6, "999");
    }

    public static void initializeValue() {
        LogMgr.log(6, "000");
        sServerConnectionUrl = FlavorConst.DEFAULT_SERVER_CONNECTION_URL;
        sServerPublicKeysForLinkageData = FlavorConst.DEFAULT_SERVER_PUBLIC_KEYS_FOR_LINKAGE_DATA;
        sServerPublicKeysForClientConfig = FlavorConst.DEFAULT_SERVER_PUBLIC_KEYS_FOR_CLIENT_CONFIG;
        LogMgr.log(6, "999");
    }

    public static String getServerConnectionUrl() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return sServerConnectionUrl;
    }

    public static Map<String, String> getServerPublicKeysForLinkageData() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return sServerPublicKeysForLinkageData;
    }

    public static Map<String, String> getServerPublicKeysForClientConfig() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return sServerPublicKeysForClientConfig;
    }
}
