package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.sws.SwsConst;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetScriptResponseJson extends ResponseJson {
    protected static final int ACCESS_TOKEN_LENGTH = 32;
    protected static final String NAME_ACCESS_TOKEN = "accessToken";
    protected static final String NAME_APDU_COMMAND_INFO_LIST = "apduCommandInfoList";
    protected static final String NAME_SCRIPT_CODE = "scriptCode";
    protected static final int SCRIPT_CODE_LENGTH = 4;

    public GetScriptResponseJson(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.sws.json.ResponseJson
    protected void checkHeaderMembers(JSONObject jSONObject) throws JSONException {
        super.checkHeaderMembers(jSONObject);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.sws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject != null) {
            JsonUtil.checkString(jSONObjectCheckObject, NAME_SCRIPT_CODE, true, 4);
            if (isScriptCodeContinue()) {
                LogMgr.log(8, "001 Script code is continue.");
                JsonUtil.checkString(jSONObjectCheckObject, NAME_ACCESS_TOKEN, true, 32);
                JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObjectCheckObject, NAME_APDU_COMMAND_INFO_LIST, true);
                if (jSONArrayCheckArray != null) {
                    new ApduCommandInfoJsonArray(jSONArrayCheckArray.toString()).checkMembers();
                }
            }
            LogMgr.log(6, "999");
            return;
        }
        LogMgr.log(1, "800 no payload.");
        throw new JSONException(ObfuscatedMsgUtil.executionPoint());
    }

    public String getScriptCode() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_SCRIPT_CODE, true, 4);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public String getAccessToken() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_ACCESS_TOKEN, true, 32);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public JSONArray getApduCommandInfoList() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObjectCheckObject, NAME_APDU_COMMAND_INFO_LIST, true);
        LogMgr.log(6, "999 ret[" + jSONArrayCheckArray + "]");
        return jSONArrayCheckArray;
    }

    public boolean isScriptCodeProcessFinished() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "0000".equals(getScriptCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isScriptCodeContinue() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = SwsConst.SCRIPT_CODE_CONTINUE_C_APDU.equals(getScriptCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isScriptCodeNotSupportedDevice() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "5081".equals(getScriptCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isScriptCodeCondition() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = SwsConst.SCRIPT_CODE_INVALID_SYSTEM_CONDITION.equals(getScriptCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }
}
