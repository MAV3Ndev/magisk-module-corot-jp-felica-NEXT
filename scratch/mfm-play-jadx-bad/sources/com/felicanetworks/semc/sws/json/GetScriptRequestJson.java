package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetScriptRequestJson extends RequestJson {
    private static final String NAME_CARRIER_ID = "carrierId";
    private static final String NAME_DEVICE_IDENTIFICATION_DATA = "deviceIdentificationData";
    private static final String NAME_DEVICE_MANUFACTURER = "deviceManufacturer";
    private static final String NAME_DEVICE_NAME = "deviceName";
    private static final String NAME_SEP_ID = "sepId";

    public void setPayload(String str, String str2, String str3, String str4, String str5, JSONObject jSONObject, String str6, JSONArray jSONArray) throws JSONException {
        LogMgr.log(8, "000");
        PayloadJson payloadJson = new PayloadJson();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(NAME_CARRIER_ID, str);
        jSONObject2.put(NAME_SEP_ID, str2);
        jSONObject2.put(NAME_DEVICE_NAME, str3);
        jSONObject2.put(NAME_DEVICE_MANUFACTURER, str4);
        if (str5 != null) {
            jSONObject2.put(NAME_DEVICE_IDENTIFICATION_DATA, str5);
        }
        payloadJson.setDeviceInfo(jSONObject2);
        payloadJson.setProcessTraceInfo(jSONObject);
        payloadJson.setAccessToken(str6);
        if (jSONArray != null) {
            payloadJson.setApduResponseInfoList(jSONArray);
        }
        putPayload(payloadJson);
        LogMgr.log(8, "999");
    }

    private static class PayloadJson extends JSONObject {
        private static final String NAME_ACCESS_TOKEN = "accessToken";
        private static final String NAME_APDU_RESPONSE_INFO_LIST = "apduResponseInfoList";
        private static final String NAME_DEVICE_INFO = "deviceInfo";
        private static final String NAME_PROCESS_TRACE_INFO = "processTraceInfo";

        private PayloadJson() {
        }

        public void setDeviceInfo(JSONObject jSONObject) throws JSONException {
            LogMgr.log(6, "000");
            put(NAME_DEVICE_INFO, jSONObject);
            LogMgr.log(6, "999");
        }

        public void setProcessTraceInfo(JSONObject jSONObject) throws JSONException {
            LogMgr.log(6, "000");
            put(NAME_PROCESS_TRACE_INFO, jSONObject);
            LogMgr.log(6, "999");
        }

        public void setAccessToken(String str) throws JSONException {
            LogMgr.log(6, "000 accessToken=[" + str + "]");
            put(NAME_ACCESS_TOKEN, str);
            LogMgr.log(6, "999");
        }

        public void setApduResponseInfoList(JSONArray jSONArray) throws JSONException {
            LogMgr.log(6, "000 apduResponseInfoList=" + jSONArray.toString());
            put(NAME_APDU_RESPONSE_INFO_LIST, jSONArray);
            LogMgr.log(6, "999");
        }
    }
}
