package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyClientEventRequestJson extends RequestJson {
    public void setPayload(String str, String str2, String str3) throws JSONException {
        LogMgr.log(8, "000 seId[" + str + "] eventType[" + str2 + "] spAppId[" + str3 + "]");
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setEventType(str2);
        payloadJson.setSpAppId(str3);
        payloadJson.setSeId(str);
        putPayload(payloadJson);
        LogMgr.log(8, "999");
    }

    private static class PayloadJson extends JSONObject {
        protected static final String NAME_EVENT_TYPE = "eventType";
        protected static final String NAME_SEID = "seId";
        protected static final String NAME_SP_APP_ID = "spAppId";

        private PayloadJson() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEventType(String str) throws JSONException {
            LogMgr.log(8, "000 eventType[" + str + "]");
            put("eventType", str);
            LogMgr.log(8, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSpAppId(String str) throws JSONException {
            LogMgr.log(8, "000 spAppId[" + str + "]");
            put(NAME_SP_APP_ID, str);
            LogMgr.log(8, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSeId(String str) throws JSONException {
            LogMgr.log(8, "000 seId[" + str + "]");
            put(NAME_SEID, str);
            LogMgr.log(8, "999");
        }
    }
}
