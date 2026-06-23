package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class InstallAppletRequestJson extends RequestJson {
    public void setPayload(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr) throws JSONException {
        LogMgr.log(8, "000 serviceId[" + str + "] spAppId[" + str2 + "] seId[" + str3 + "] spAppletId[" + str4 + "] spAppletVersion[" + str5 + "] spSdKeyVersion[" + str6 + "] accessAllowedSpAppIdList[" + Arrays.toString(strArr) + "]");
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setServiceId(str);
        payloadJson.setSpAppId(str2);
        payloadJson.setSeId(str3);
        payloadJson.setSpAppletId(str4);
        payloadJson.setSpAppletVersion(str5);
        payloadJson.setSpSdKeyVersion(str6);
        if (strArr != null) {
            payloadJson.setAccessAllowedSpAppIdList(strArr);
        }
        putPayload(payloadJson);
        LogMgr.log(8, "999");
    }

    private static class PayloadJson extends JSONObject {
        private static final String NAME_ACCESS_ALLOWED_SP_APP_ID_LIST = "accessAllowedSpAppIdList";
        private static final String NAME_SEID = "seId";
        private static final String NAME_SERVICE_ID = "serviceId";
        private static final String NAME_SP_APPLET_ID = "spAppletId";
        private static final String NAME_SP_APPLET_VERSION = "spAppletVersion";
        private static final String NAME_SP_APP_ID = "spAppId";
        private static final String NAME_SP_SD_KEY_VERSION = "spSdKeyVersion";

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
        public void setSpSdKeyVersion(String str) throws JSONException {
            LogMgr.log(8, "000 spSdKeyVersion[" + str + "]");
            put("spSdKeyVersion", str);
            LogMgr.log(8, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAccessAllowedSpAppIdList(String[] strArr) throws JSONException {
            LogMgr.log(8, "000 accessAllowedSpAppIdList[" + Arrays.toString(strArr) + "]");
            JSONArray jSONArray = new JSONArray();
            for (String str : strArr) {
                jSONArray.put(str);
            }
            put("accessAllowedSpAppIdList", jSONArray);
            LogMgr.log(8, "999");
        }
    }
}
