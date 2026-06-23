package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RequestJson extends JSONObject {
    private static final String NAME_ACCESS_TOKEN = "accessToken";
    private static final String NAME_HEADER = "header";
    private static final String NAME_LOGIN_TOKEN_ID = "loginTokenId";
    private static final String NAME_OPERATION_ID = "operationId";
    private static final String NAME_PAYLOAD = "payload";
    private static final String NAME_REQUEST_ID = "requestId";

    public void setRequestId(String requestId) throws JSONException {
        putHeaderMember(NAME_REQUEST_ID, requestId);
    }

    public void setOperationId(String operationId) throws JSONException {
        putHeaderMember("operationId", operationId);
    }

    public void setAccessToken(String accessToken) throws JSONException {
        putHeaderMember(NAME_ACCESS_TOKEN, accessToken);
    }

    public void setLoginTokenId(String loginTokenId) throws JSONException {
        putHeaderMember(NAME_LOGIN_TOKEN_ID, loginTokenId);
    }

    private void putHeaderMember(String name, String value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_HEADER);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(name, value);
        put(NAME_HEADER, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String name, boolean value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(name, value);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String name, int value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(name, value);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String name, String value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(name, value);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected void putPayloadMember(String name, JSONObject value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(name, value);
        put(NAME_PAYLOAD, jSONObjectOptJSONObject);
    }

    protected JSONObject optPayloadJSONObjectMember(String name) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optJSONObject(name);
        }
        return null;
    }
}
