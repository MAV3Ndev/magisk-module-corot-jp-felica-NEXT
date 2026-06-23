package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class UpgradeAppletRequestJson extends RequestJson {
    public void setPayload(String str, String str2, String str3, String str4, String str5, String str6) throws JSONException {
        LogMgr.log(8, "000 serviceId[" + str + "] spAppId[" + str2 + "] seId[" + str3 + "] spAppletId[" + str4 + "] oldSpAppletVersion[" + str5 + "] newSpAppletVersion[" + str6 + "]");
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setServiceId(str);
        payloadJson.setSpAppId(str2);
        payloadJson.setSeId(str3);
        payloadJson.setSpAppletId(str4);
        payloadJson.setOldSpAppletVersion(str5);
        payloadJson.setNewSpAppletVersion(str6);
        putPayload(payloadJson);
        LogMgr.log(8, "999");
    }

    private static class PayloadJson extends JSONObject {
        private static final String NAME_NEW_SP_APPLET_VERSION = "newSpAppletVersion";
        private static final String NAME_OLD_SP_APPLET_VERSION = "oldSpAppletVersion";
        private static final String NAME_SEID = "seId";
        private static final String NAME_SERVICE_ID = "serviceId";
        private static final String NAME_SP_APPLET_ID = "spAppletId";
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
        public void setOldSpAppletVersion(String str) throws JSONException {
            LogMgr.log(8, "000 oldSpAppletVersion[" + str + "]");
            put("oldSpAppletVersion", str);
            LogMgr.log(8, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNewSpAppletVersion(String str) throws JSONException {
            LogMgr.log(8, "000 newSpAppletVersion[" + str + "]");
            put("newSpAppletVersion", str);
            LogMgr.log(8, "999");
        }
    }
}
