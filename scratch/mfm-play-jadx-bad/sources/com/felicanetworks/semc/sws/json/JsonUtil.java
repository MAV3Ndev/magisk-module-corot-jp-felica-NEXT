package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class JsonUtil {
    public static JSONObject checkObject(JSONObject jSONObject, String str, boolean z) throws JSONException {
        LogMgr.log(7, "000");
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (!z) {
                return null;
            }
            LogMgr.log(1, "800 : specified object does not exist.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(str);
        if (jSONObjectOptJSONObject != null) {
            LogMgr.log(7, "999");
            return jSONObjectOptJSONObject;
        }
        if (!z) {
            return null;
        }
        LogMgr.log(1, "801 : specified object does not exist.");
        throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
    }

    public static JSONArray checkArray(JSONObject jSONObject, String str, boolean z) throws JSONException {
        LogMgr.log(7, "000");
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (!z) {
                return null;
            }
            LogMgr.log(1, "800 : specified object does not exist.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(str);
        if (jSONArrayOptJSONArray != null) {
            LogMgr.log(7, "999");
            return jSONArrayOptJSONArray;
        }
        if (!z) {
            return null;
        }
        LogMgr.log(1, "801 : specified object does not exist.");
        throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
    }

    public static String checkString(JSONObject jSONObject, String str, boolean z, int i) throws JSONException {
        LogMgr.log(7, "000");
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (!z) {
                return null;
            }
            LogMgr.log(1, "800 : specified object does not exist.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        String strOptString = jSONObject.optString(str);
        if (strOptString.isEmpty()) {
            if (!z) {
                return null;
            }
            LogMgr.log(1, "801 : specified object does not exist.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        if (i > 0 && strOptString.length() != i) {
            LogMgr.log(1, "802 : invalid length.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        LogMgr.log(7, "999");
        return strOptString;
    }

    public static String checkString(JSONObject jSONObject, String str, boolean z, int i, int i2) throws JSONException {
        LogMgr.log(7, "000");
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (!z) {
                return null;
            }
            LogMgr.log(1, "800 : specified object does not exist.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        String string = jSONObject.getString(str);
        if (string.isEmpty()) {
            if (!z) {
                return null;
            }
            LogMgr.log(1, "801 : specified object does not exist.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        if (string.length() < i) {
            LogMgr.log(1, "802 : invalid length(too short).");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        if (string.length() > i2) {
            LogMgr.log(1, "803 : invalid length(too long).");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        LogMgr.log(7, "999");
        return string;
    }

    public static boolean checkBoolean(JSONObject jSONObject, String str, boolean z) throws JSONException {
        LogMgr.log(7, "000");
        if (jSONObject.has(str) && !jSONObject.isNull(str)) {
            LogMgr.log(7, "999");
            return jSONObject.getBoolean(str);
        }
        if (z) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        return false;
    }

    public static int checkInt(JSONObject jSONObject, String str, boolean z, int i, boolean z2, int i2, boolean z3, int i3) throws JSONException {
        LogMgr.log(7, "000");
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (z) {
                throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
            }
            return i;
        }
        int i4 = jSONObject.getInt(str);
        if (z2 && i4 < i2) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        if (z3 && i4 > i3) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint(str));
        }
        LogMgr.log(7, "999");
        return i4;
    }
}
