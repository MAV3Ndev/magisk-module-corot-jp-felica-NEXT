package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetClientConfigurationResponseJson extends ResponseJson {
    private static final String NAME_CLIENT_CONFIGURATION = "clientConfiguration";

    public GetClientConfigurationResponseJson(String str) throws JSONException {
        super(str);
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
    }

    @Override // com.felicanetworks.semc.sws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        LogMgr.log(8, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject != null) {
            new ClientConfigurationJson(new JwsObject(JsonUtil.checkString(jSONObjectCheckObject, NAME_CLIENT_CONFIGURATION, true, 0)).getPayload()).checkMembers();
            LogMgr.log(8, "999");
        } else {
            LogMgr.log(1, "800 no payload.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
    }

    public String getClientConfiguration() {
        LogMgr.log(6, "000");
        String strCheckString = null;
        try {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", false);
            if (jSONObjectCheckObject == null) {
                return null;
            }
            strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_CLIENT_CONFIGURATION, false, 0);
            LogMgr.log(6, "999 ret[" + strCheckString + "]");
        } catch (JSONException unused) {
        }
        return strCheckString;
    }
}
