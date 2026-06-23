package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.fws.FwsConst;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class ResponseJson extends JSONObject {
    private static final int LEN_RESULT_CODE = 4;
    protected static final String NAME_ACCESSED_CARD = "accessedCard";
    protected static final String NAME_COMMAND_APDU_INFO = "commandApduInfo";
    protected static final String NAME_HEADER = "header";
    protected static final String NAME_NEXT_ACCESS_TOKEN = "nextAccessToken";
    protected static final String NAME_PAYLOAD = "payload";
    protected static final String NAME_RESULT_CODE = "resultCode";
    protected static final String NAME_RESULT_MESSAGE = "resultMessage";
    protected static final String NAME_RETRY_AFTER = "retryAfter";
    protected static final String NAME_TCAP_REQUEST_JSON = "tcapRequest";

    protected abstract void checkPayloadMembers() throws JSONException;

    public ResponseJson(String str) throws JSONException {
        super(str);
    }

    public void checkMembers() throws JSONException {
        checkHeaderMembers(JsonUtil.checkObject((JSONObject) this, NAME_HEADER, true));
        checkPayloadMembers();
    }

    protected void checkHeaderMembers(JSONObject jSONObject) throws JSONException {
        JsonUtil.checkString(jSONObject, NAME_RESULT_CODE, true, 4);
        JsonUtil.checkString(jSONObject, NAME_RESULT_MESSAGE, false, 0);
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
        return Pattern.compile(FwsConst.RESULT_CODE_TYPE_PTN_SUCCESS).matcher(optResultCode()).find();
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

    private String optHeaderStringMember(String str) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_HEADER);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optString(str, null);
        }
        return null;
    }

    protected boolean hasPayloadMember(String str) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        return (jSONObjectOptJSONObject == null || !jSONObjectOptJSONObject.has(str) || jSONObjectOptJSONObject.isNull(str)) ? false : true;
    }

    protected String optPayloadStringMember(String str) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optString(str, null);
        }
        return null;
    }

    protected JSONObject optPayloadJSONObjectMember(String str) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optJSONObject(str);
        }
        return null;
    }

    public int optRetryAfter() {
        String strOptHeaderStringMember;
        if (isTimedRetryRequest() && (strOptHeaderStringMember = optHeaderStringMember(NAME_RETRY_AFTER)) != null) {
            try {
                return Integer.parseInt(strOptHeaderStringMember);
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    protected JSONArray optPayloadJSONArrayMember(String str) {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_PAYLOAD);
        if (jSONObjectOptJSONObject != null) {
            return jSONObjectOptJSONObject.optJSONArray(str);
        }
        return null;
    }
}
