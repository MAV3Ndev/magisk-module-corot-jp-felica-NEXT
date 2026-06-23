package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteAppletRequestJson extends RequestJson {
    public void setPayload(String str, String str2, String str3, String str4, String str5, String str6) throws JSONException {
        LogMgr.log(8, "000 serviceId[" + str + "] spAppId[" + str2 + "] seId[" + str3 + "] spAppletId[" + str4 + "] spAppletVersion[" + str5 + "] processingMode[" + str6 + "]");
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setServiceId(str);
        payloadJson.setSpAppId(str2);
        payloadJson.setSeId(str3);
        payloadJson.setSpAppletId(str4);
        if (str5 != null) {
            payloadJson.setSpAppletVersion(str5);
        }
        payloadJson.setProcessingMode(str6);
        putPayload(payloadJson);
        LogMgr.log(8, "999");
    }

    private static class PayloadJson extends JSONObject {
        private static final String DEFAULT_PROCESSING_MODE = "PULL";
        private static final String NAME_PROCESSING_MODE = "processingMode";
        private static final String NAME_SEID = "seId";
        private static final String NAME_SERVICE_ID = "serviceId";
        private static final String NAME_SP_APPLET_ID = "spAppletId";
        private static final String NAME_SP_APPLET_VERSION = "spAppletVersion";
        private static final String NAME_SP_APP_ID = "spAppId";

        private PayloadJson() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setServiceId(String str) throws JSONException {
            LogMgr.log(8, "000 serviceId[" + str + "]");
            put("serviceId", str);
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

        /* JADX INFO: Access modifiers changed from: private */
        public void setSpAppletId(String str) throws JSONException {
            LogMgr.log(8, "000 spAppletId[" + str + "]");
            put("spAppletId", str);
            LogMgr.log(8, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSpAppletVersion(String str) throws JSONException {
            LogMgr.log(8, "000 spAppletVersion[" + str + "]");
            put("spAppletVersion", str);
            LogMgr.log(8, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProcessingMode(String str) throws JSONException {
            LogMgr.log(8, "000 processingMode[" + str + "]");
            if (str == null) {
                put(NAME_PROCESSING_MODE, DEFAULT_PROCESSING_MODE);
            } else {
                put(NAME_PROCESSING_MODE, str);
            }
            LogMgr.log(8, "999");
        }
    }
}
