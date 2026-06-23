package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.fws.FwsConst;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ResponseJson extends JSONObject {
    private static final int LEN_RESULT_CODE = 4;
    protected static final String NAME_COMMUNICATION_SERVER_ERROR_INFO = "communicationServerErrorInfo";
    protected static final String NAME_HEADER = "header";
    protected static final String NAME_NEXT_ACCESS_TOKEN = "nextAccessToken";
    protected static final String NAME_PAYLOAD = "payload";
    protected static final String NAME_RESULT_CODE = "resultCode";
    protected static final String NAME_RESULT_MESSAGE = "resultMessage";
    protected static final String NAME_RETRY_AFTER = "retryAfter";

    protected abstract void checkPayloadMembers() throws JSONException;

    public ResponseJson(String json) throws JSONException {
        super(json);
    }

    public void checkMembers() throws JSONException {
        checkHeaderMembers(JsonUtil.checkObject((JSONObject) this, NAME_HEADER, true));
        checkPayloadMembers();
    }

    protected void checkHeaderMembers(JSONObject header) throws JSONException {
        JsonUtil.checkString(header, NAME_RESULT_CODE, true, 4);
        JsonUtil.checkString(header, NAME_RESULT_MESSAGE, false, 0);
    }

    public String optResultCode() {
        return optHeaderStringMember(NAME_RESULT_CODE);
    }

    public String optResultMessage() {
        return optHeaderStringMember(NAME_RESULT_MESSAGE);
    }

    public String optNextAccessToken() {
        return optHeaderStringMember(NAME_NEXT_ACCESS_TOKEN);
    }

    public boolean isSuccessType() {
        return Pattern.compile("^0...$").matcher(optResultCode()).find();
    }

    public boolean isSuccess() {
        return "0000".equals(optResultCode());
    }

    public boolean isContinue() {
        return FwsConst.RESULT_CODE_CONTINUE.equals(optResultCode());
    }

    public boolean isTimedRetryRequest() {
        return FwsConst.RESULT_CODE_TIMEDRETRYREQUEST.equals(optResultCode());
    }

    private String optHeaderStringMember(String name) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_HEADER);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optString(name, null);
        }
        return null;
    }

    protected boolean hasPayloadMember(String name) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        return (jSONObjectOptJSONObject == null || !jSONObjectOptJSONObject.has(name) || jSONObjectOptJSONObject.isNull(name)) ? false : true;
    }

    protected String optPayloadStringMember(String name) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optString(name, null);
        }
        return null;
    }

    protected JSONObject optPayloadJSONObjectMember(String name) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optJSONObject(name);
        }
        return null;
    }

    public int optRetryAfter() {
        String strOptHeaderStringMember;
        if (!isTimedRetryRequest() || (strOptHeaderStringMember = optHeaderStringMember(NAME_RETRY_AFTER)) == null) {
            return -1;
        }
        try {
            return Integer.parseInt(strOptHeaderStringMember);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public JSONObject optCommunicationServerErrorInfo() throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_HEADER);
        if (jSONObjectOptJSONObject != null) {
            return JsonUtil.checkObject(jSONObjectOptJSONObject, NAME_COMMUNICATION_SERVER_ERROR_INFO, false);
        }
        return null;
    }

    protected JSONArray optPayloadJSONArrayMember(String name) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optJSONArray(name);
        }
        return null;
    }
}
