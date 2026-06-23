package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class NotifyDeviceMessagePayloadJson extends JSONObject {
    private static final int LOG_INFO_LENGTH = 256;
    private static final String NAME_CLIENT_ID = "clientId";
    private static final String NAME_CNONCE = "cnonce";
    private static final String NAME_CURRENT_TIME = "currentTime";
    private static final String NAME_DEVICE_NAME = "deviceName";
    private static final String NAME_LOG_INFO_LIST = "logInfoList";
    private static final String NAME_LOG_MESSAGE = "logMessage";
    private static final String NAME_MESSAGE_ID = "messageId";
    private static final String NAME_OPERATION_ID = "operationId";
    private static final String NAME_OS_VERSION = "osVersion";
    private static final String NAME_PLATFORM_TYPE = "platformType";

    public void setCnonce(String str) throws JSONException {
        put(NAME_CNONCE, str);
    }

    public void setClientId(String str) throws JSONException {
        put(NAME_CLIENT_ID, str);
    }

    public void setCurrentTime(String str) throws JSONException {
        put(NAME_CURRENT_TIME, str);
    }

    public void setPlatformType(String str) throws JSONException {
        put(NAME_PLATFORM_TYPE, str);
    }

    public void setDeviceName(String str) throws JSONException {
        put(NAME_DEVICE_NAME, str);
    }

    public void setOsVersion(String str) throws JSONException {
        put(NAME_OS_VERSION, str);
    }

    public void setLogInfoList(String[] strArr, String[] strArr2, String[] strArr3) throws JSONException {
        if (strArr == null) {
            throw new JSONException("operationId is null.");
        }
        if (strArr2 == null) {
            throw new JSONException("messageId is null.");
        }
        if (strArr3 == null) {
            throw new JSONException("logInfoList is null.");
        }
        if (strArr.length != strArr3.length) {
            throw new JSONException("The number of operationId arrays is inconsistent.");
        }
        if (strArr2.length != strArr3.length) {
            throw new JSONException("The number of messageId arrays is inconsistent.");
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < strArr3.length; i++) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(NAME_OPERATION_ID, strArr[i]);
            jSONObject.put(NAME_MESSAGE_ID, strArr2[i]);
            jSONObject.put(NAME_LOG_MESSAGE, String.format("%.256s", strArr3[i]));
            jSONArray.put(jSONObject);
        }
        put(NAME_LOG_INFO_LIST, jSONArray);
    }
}
