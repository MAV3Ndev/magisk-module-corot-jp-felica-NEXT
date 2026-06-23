package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ClientControlInfoJsonArray extends JSONArray {
    private static final String KEY_KEY = "key";
    private static final String KEY_MINIMUM_REQUIRED_SDK_VERSION = "minimumRequiredSdkVersion";
    private static final String KEY_MINIMUM_REQUIRED_VERSION = "minimumRequiredVersion";
    private static final String KEY_PERIODIC_WORK_START_TIME_MAX = "periodicWorkStartTimeMax";
    private static final String KEY_PERIODIC_WORK_START_TIME_MIN = "periodicWorkStartTimeMin";
    private static final String KEY_VALUE = "value";

    public ClientControlInfoJsonArray(String str) throws JSONException {
        super(str);
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
    }

    protected void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        if (length() == 0) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        for (int i = 0; i < length(); i++) {
            JSONObject jSONObject = getJSONObject(i);
            JsonUtil.checkString(jSONObject, "key", true, 1, 255);
            JsonUtil.checkString(jSONObject, "value", true, 1, 1024);
        }
        LogMgr.log(8, "999");
    }

    public Map<String, String> getClientControlInfo() throws JSONException {
        LogMgr.log(7, "000");
        HashMap map = new HashMap();
        for (int i = 0; i < length(); i++) {
            JSONObject jSONObject = getJSONObject(i);
            map.put(jSONObject.getString("key"), jSONObject.getString("value"));
        }
        LogMgr.log(7, "999");
        return map;
    }

    public String getMinimumRequiredVersion() {
        String str;
        LogMgr.log(7, "000");
        try {
            str = getClientControlInfo().get(KEY_MINIMUM_REQUIRED_VERSION);
        } catch (JSONException unused) {
            LogMgr.log(2, "700 failed to get Client Control Information");
            str = null;
        }
        LogMgr.log(7, "999 version=" + str);
        return str;
    }

    public String getPeriodicWorkStartTimeMin() {
        String str;
        LogMgr.log(7, "000");
        try {
            str = getClientControlInfo().get("periodicWorkStartTimeMin");
        } catch (JSONException unused) {
            LogMgr.log(2, "700 failed to get Client Control Information");
            str = null;
        }
        LogMgr.log(7, "999 periodicWorkStartTimeMin=" + str);
        return str;
    }

    public String getPeriodicWorkStartTimeMax() {
        String str;
        LogMgr.log(7, "000");
        try {
            str = getClientControlInfo().get("periodicWorkStartTimeMax");
        } catch (JSONException unused) {
            LogMgr.log(2, "700 failed to get Client Control Information");
            str = null;
        }
        LogMgr.log(7, "999 periodicWorkStartTimeMax=" + str);
        return str;
    }

    public String getMinimumRequiredSdkVersion() {
        String str;
        LogMgr.log(7, "000");
        try {
            str = getClientControlInfo().get(KEY_MINIMUM_REQUIRED_SDK_VERSION);
        } catch (JSONException unused) {
            LogMgr.log(2, "700 failed to get Client Control Information");
            str = null;
        }
        LogMgr.log(7, "999 minimumRequiredSdkVersion=" + str);
        return str;
    }
}
