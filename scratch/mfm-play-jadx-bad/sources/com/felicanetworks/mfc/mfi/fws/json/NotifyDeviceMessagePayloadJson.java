package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
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

    public void setCnonce(String value) throws JSONException {
        put(NAME_CNONCE, value);
    }

    public void setClientId(String clientId) throws JSONException {
        put("clientId", clientId);
    }

    public void setCurrentTime(String currentTime) throws JSONException {
        put(NAME_CURRENT_TIME, currentTime);
    }

    public void setPlatformType(String platformType) throws JSONException {
        put(NAME_PLATFORM_TYPE, platformType);
    }

    public void setDeviceName(String deviceName) throws JSONException {
        put(NAME_DEVICE_NAME, deviceName);
    }

    public void setOsVersion(String osVersion) throws JSONException {
        put(NAME_OS_VERSION, osVersion);
    }

    public void setLogInfoList(String[] operationId, String[] messageId, String[] logInfoList) throws JSONException {
        if (operationId == null) {
            throw new JSONException("operationId is null.");
        }
        if (messageId == null) {
            throw new JSONException("messageId is null.");
        }
        if (logInfoList == null) {
            throw new JSONException("logInfoList is null.");
        }
        if (operationId.length != logInfoList.length) {
            throw new JSONException("The number of operationId arrays is inconsistent.");
        }
        if (messageId.length != logInfoList.length) {
            throw new JSONException("The number of messageId arrays is inconsistent.");
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < logInfoList.length; i++) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("operationId", operationId[i]);
            jSONObject.put(NAME_MESSAGE_ID, messageId[i]);
            jSONObject.put(NAME_LOG_MESSAGE, String.format("%.256s", logInfoList[i]));
            jSONArray.put(jSONObject);
        }
        put(NAME_LOG_INFO_LIST, jSONArray);
    }
}
