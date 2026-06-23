package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class RegisterDeviceTokenRequestJson extends RequestJson {
    public void setPayload(String str, String str2) throws JSONException {
        LogMgr.log(8, "000 seId[" + str + "] deviceToken[" + str2 + "]");
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setSeid(str);
        payloadJson.setDeviceToken(str2);
        putPayload(payloadJson);
        LogMgr.log(8, "999");
    }

    private static class PayloadJson extends JSONObject {
        private static final String NAME_DEVICE_TOKEN = "deviceToken";
        private static final String NAME_SEID = "seId";

        private PayloadJson() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSeid(String str) throws JSONException {
            LogMgr.log(8, "000 seId[" + str + "]");
            put(NAME_SEID, str);
            LogMgr.log(8, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDeviceToken(String str) throws JSONException {
            LogMgr.log(8, "000 deviceToken[" + str + "]");
            put(NAME_DEVICE_TOKEN, str);
            LogMgr.log(8, "999");
        }
    }
}
