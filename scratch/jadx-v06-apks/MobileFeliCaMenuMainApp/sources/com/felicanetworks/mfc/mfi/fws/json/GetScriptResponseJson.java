package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GetScriptResponseJson extends ResponseJson {
    protected static final String NAME_COMMAND_ADPU_INFO = "commandApduInfo";
    protected static final String NAME_TCAP_REQUEST = "tcapRequest";
    protected static final String NAME_URL = "url";

    public GetScriptResponseJson(String str) throws JSONException {
        super(str);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkHeaderMembers(JSONObject jSONObject) throws JSONException {
        super.checkHeaderMembers(jSONObject);
        if (isContinue()) {
            JsonUtil.checkString(jSONObject, "nextAccessToken", true, 0);
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isContinue()) {
            LogMgr.log(6, "Continue request received.");
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", false);
            if (jSONObjectCheckObject != null) {
                JSONObject jSONObjectCheckObject2 = JsonUtil.checkObject(jSONObjectCheckObject, NAME_TCAP_REQUEST, false);
                if (jSONObjectCheckObject2 != null) {
                    JsonUtil.checkString(jSONObjectCheckObject2, "url", true, 0);
                    if (JsonUtil.checkObject(jSONObjectCheckObject, NAME_COMMAND_ADPU_INFO, false) == null) {
                        LogMgr.log(6, "%s found.", NAME_TCAP_REQUEST);
                        return;
                    } else {
                        LogMgr.log(6, "TcapRequest,commandApdu both specified");
                        throw new JSONException("TcapRequest,commandApdu both specified");
                    }
                }
                if (JsonUtil.checkObject(jSONObjectCheckObject, NAME_COMMAND_ADPU_INFO, false) != null) {
                    LogMgr.log(6, "%s found.", NAME_COMMAND_ADPU_INFO);
                    return;
                } else {
                    LogMgr.log(6, "null command response");
                    return;
                }
            }
            LogMgr.log(6, "no payload");
            return;
        }
        if (isTimedRetryRequest()) {
            LogMgr.log(6, "time specified retry request received.");
        } else {
            LogMgr.log(6, "finish request received.");
        }
    }

    public boolean hasPayload() {
        JSONObject jSONObjectOptJSONObject = optJSONObject("payload");
        return (jSONObjectOptJSONObject == null || jSONObjectOptJSONObject == JSONObject.NULL) ? false : true;
    }

    public boolean isTCAPRequest() {
        if (isContinue()) {
            return hasPayloadMember(NAME_TCAP_REQUEST);
        }
        return false;
    }

    public boolean isAPDURequest() {
        if (isContinue()) {
            return hasPayloadMember(NAME_COMMAND_ADPU_INFO);
        }
        return false;
    }

    public String optUrl() {
        JSONObject jSONObjectOptPayloadJSONObjectMember = optPayloadJSONObjectMember(NAME_TCAP_REQUEST);
        if (jSONObjectOptPayloadJSONObjectMember != null) {
            return jSONObjectOptPayloadJSONObjectMember.optString("url", null);
        }
        return null;
    }

    public JSONObject optCommandApduInfo() {
        return optPayloadJSONObjectMember(NAME_COMMAND_ADPU_INFO);
    }
}
