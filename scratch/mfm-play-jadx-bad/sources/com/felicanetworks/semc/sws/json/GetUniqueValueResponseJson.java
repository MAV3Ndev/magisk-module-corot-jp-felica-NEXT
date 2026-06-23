package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetUniqueValueResponseJson extends ResponseJson {
    private static final String NAME_UNIQUE_VALUE = "uniqueValue";
    private static final int UNIQUE_VALUE_LENGTH = 32;

    public GetUniqueValueResponseJson(String str) throws JSONException {
        super(str);
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
    }

    @Override // com.felicanetworks.semc.sws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        LogMgr.log(8, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject != null) {
            JsonUtil.checkString(jSONObjectCheckObject, NAME_UNIQUE_VALUE, true, 32);
            LogMgr.log(8, "999");
        } else {
            LogMgr.log(1, "800 no payload.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
    }

    public String getUniqueValue() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_UNIQUE_VALUE, true, 32);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }
}
