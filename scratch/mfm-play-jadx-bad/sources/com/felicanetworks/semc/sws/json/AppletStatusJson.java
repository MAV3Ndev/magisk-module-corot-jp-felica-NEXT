package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class AppletStatusJson extends JSONObject {
    private static final String KEY_ACCESS_ALLOWED_SP_APP_ID_LIST = "accessAllowedSpAppIdList";
    private static final String KEY_APPLET_STATUS_LIST = "appletStatusList";
    private static final String KEY_NEW_SP_APPLET_VERSION = "newSpAppletVersion";
    private static final String KEY_OLD_SP_APPLET_VERSION = "oldSpAppletVersion";
    private static final String KEY_SP_APPLET_VERSION = "spAppletVersion";
    private static final String KEY_STATUS = "status";

    public AppletStatusJson(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    protected void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, KEY_APPLET_STATUS_LIST, false);
        if (jSONArrayCheckArray != null) {
            for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
                JSONObject jSONObject = jSONArrayCheckArray.getJSONObject(i);
                JsonUtil.checkString(jSONObject, "spAppletVersion", false, 4);
                JsonUtil.checkString(jSONObject, "oldSpAppletVersion", false, 4);
                JsonUtil.checkString(jSONObject, "newSpAppletVersion", false, 4);
                JsonUtil.checkString(jSONObject, "status", true, 0);
            }
        } else {
            LogMgr.log(2, "700 appletStatusList is not exist.");
        }
        JSONArray jSONArrayCheckArray2 = JsonUtil.checkArray(this, "accessAllowedSpAppIdList", false);
        if (jSONArrayCheckArray2 != null) {
            for (int i2 = 0; i2 < jSONArrayCheckArray2.length(); i2++) {
                if (jSONArrayCheckArray2.getString(i2).length() != 8) {
                    LogMgr.log(1, "701 : invalid length.");
                    throw new JSONException(ObfuscatedMsgUtil.executionPoint("accessAllowedSpAppIdList"));
                }
            }
        } else {
            LogMgr.log(2, "702 accessAllowedSpAppIdList is not exist.");
        }
        LogMgr.log(8, "999");
    }

    public JSONObject getAppletStatusList(String str) {
        LogMgr.log(7, "000");
        JSONObject jSONObjectOptJSONObject = optJSONObject(str);
        if (jSONObjectOptJSONObject == null) {
            LogMgr.log(7, "997 not exist at [" + str + "]");
            return null;
        }
        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(KEY_APPLET_STATUS_LIST);
        if (jSONObjectOptJSONObject2 == null) {
            LogMgr.log(7, "998 not exist at appletStatusList");
        }
        LogMgr.log(7, "999");
        return jSONObjectOptJSONObject2;
    }

    public JSONObject getAccessAllowedSpAppIdList(String str) {
        LogMgr.log(7, "000");
        JSONObject jSONObjectOptJSONObject = optJSONObject(str);
        if (jSONObjectOptJSONObject == null) {
            LogMgr.log(7, "997 not exist at [" + str + "]");
            return null;
        }
        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("accessAllowedSpAppIdList");
        if (jSONObjectOptJSONObject2 == null) {
            LogMgr.log(7, "998 not exist at accessAllowedSpAppIdList");
        }
        LogMgr.log(7, "999");
        return jSONObjectOptJSONObject2;
    }
}
