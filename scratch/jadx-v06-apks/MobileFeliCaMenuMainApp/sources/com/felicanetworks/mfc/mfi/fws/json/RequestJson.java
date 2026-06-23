package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class RequestJson extends JSONObject {
    private static final String NAME_ACCESS_TOKEN = "accessToken";
    private static final String NAME_HEADER = "header";
    private static final String NAME_LOGIN_TOKEN_ID = "loginTokenId";
    private static final String NAME_OPERATION_ID = "operationId";
    private static final String NAME_PAYLOAD = "payload";
    private static final String NAME_REQUEST_ID = "requestId";

    public void setRequestId(String str) throws JSONException {
        putHeaderMember(NAME_REQUEST_ID, str);
    }

    public void setOperationId(String str) throws JSONException {
        putHeaderMember(NAME_OPERATION_ID, str);
    }

    public void setAccessToken(String str) throws JSONException {
        putHeaderMember(NAME_ACCESS_TOKEN, str);
    }

    public void setLoginTokenId(String str) throws JSONException {
        putHeaderMember(NAME_LOGIN_TOKEN_ID, str);
    }

    private void putHeaderMember(String str, String str2) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_HEADER);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, str2);
        put(NAME_HEADER, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String str, boolean z) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, z);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String str, int i) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, i);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String str, String str2) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, str2);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String str, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, jSONObject);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected JSONObject optPayloadJSONObjectMember(String str) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optJSONObject(str);
        }
        return null;
    }
}
