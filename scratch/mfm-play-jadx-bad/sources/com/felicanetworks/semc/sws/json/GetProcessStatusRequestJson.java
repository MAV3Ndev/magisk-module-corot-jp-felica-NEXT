package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetProcessStatusRequestJson extends RequestJson {
    public void setPayload(String str) throws JSONException {
        LogMgr.log(8, "000 processId[" + str + "]");
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setProcessId(str);
        putPayload(payloadJson);
        LogMgr.log(8, "999");
    }

    private static class PayloadJson extends JSONObject {
        private static final String NAME_PROCESS_ID = "processId";

        private PayloadJson() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProcessId(String str) throws JSONException {
            LogMgr.log(8, "000 processId[" + str + "]");
            put(NAME_PROCESS_ID, str);
            LogMgr.log(8, "999");
        }
    }
}
