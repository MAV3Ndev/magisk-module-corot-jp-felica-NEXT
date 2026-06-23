package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RequestJson extends JSONObject {
    private static final String NAME_CLIENT_ID = "clientId";
    private static final String NAME_HEADER = "header";
    private static final String NAME_OPERATION_ID = "operationId";
    private static final String NAME_OPERATION_TYPE = "operationType";
    private static final String NAME_PAYLOAD = "payload";
    private static final String NAME_REQUEST_ID = "requestId";

    public void setRequestId(String str) throws JSONException {
        LogMgr.log(6, "000 requestId=[" + str + "]");
        putHeaderMember(NAME_REQUEST_ID, str);
        LogMgr.log(6, "999");
    }

    public void setClientId(String str) throws JSONException {
        LogMgr.log(6, "000 clientId=[" + str + "]");
        putHeaderMember("clientId", str);
        LogMgr.log(6, "999");
    }

    public void setOperationType(String str) throws JSONException {
        LogMgr.log(6, "000 operationType=[" + str + "]");
        putHeaderMember("operationType", str);
        LogMgr.log(6, "999");
    }

    public void setOperationId(String str) throws JSONException {
        LogMgr.log(6, "000 operationId=[" + str + "]");
        putHeaderMember("operationId", str);
        LogMgr.log(6, "999");
    }

    private void putHeaderMember(String str, String str2) throws JSONException {
        LogMgr.log(8, "000 name[" + str + "] value[" + str2 + "]");
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_HEADER);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, str2);
        put(NAME_HEADER, jSONObjectOptJSONObject);
        LogMgr.log(8, "999");
    }

    protected void putPayload(JSONObject jSONObject) throws JSONException {
        LogMgr.log(8, "000 value[" + jSONObject + "]");
        put(NAME_PAYLOAD, jSONObject);
        LogMgr.log(8, "999");
    }

    protected void putPayloadMember(String str, String str2) throws JSONException {
        LogMgr.log(8, "000 name[" + str + "] value[" + str2 + "]");
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, str2);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
        LogMgr.log(8, "999");
    }
}
