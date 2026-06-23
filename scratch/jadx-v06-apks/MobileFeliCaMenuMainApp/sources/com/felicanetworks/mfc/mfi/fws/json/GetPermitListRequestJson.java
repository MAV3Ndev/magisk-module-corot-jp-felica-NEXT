package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GetPermitListRequestJson extends RequestJson {
    protected static final String NAME_CONDITIONS = "conditions";
    protected static final String NAME_PLATFORM_TYPE = "platformType";
    protected static final String NAME_SEP_ID = "sepId";
    protected static final String NAME_SE_INFO = "seInfo";
    protected static final String NAME_WALLET_APP_CALLER_INFO_LIST = "walletAppCallerInfoList";
    protected static final String NAME_WALLET_APP_IDENTIFIABLE_INFO = "walletAppIdentifiableInfo";

    public void setPlatformType(String str) throws JSONException {
        putConditionsMember(NAME_PLATFORM_TYPE, str);
    }

    public void setWalletAppCallerInfoList(JSONArray jSONArray) throws JSONException {
        putConditionsMember(NAME_WALLET_APP_CALLER_INFO_LIST, jSONArray);
    }

    public void setWalletAppIdentifiableInfo(String str) throws JSONException {
        putConditionsMember(NAME_WALLET_APP_IDENTIFIABLE_INFO, str);
    }

    public void setSepId(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_SEP_ID, str);
        putConditionsMember(NAME_SE_INFO, jSONObject);
    }

    protected void putConditionsMember(String str, JSONArray jSONArray) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_CONDITIONS);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, jSONArray);
        put(NAME_CONDITIONS, jSONObjectOptJSONObject);
    }

    protected void putConditionsMember(String str, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_CONDITIONS);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, jSONObject);
        put(NAME_CONDITIONS, jSONObjectOptJSONObject);
    }

    protected void putConditionsMember(String str, String str2) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_CONDITIONS);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(str, str2);
        put(NAME_CONDITIONS, jSONObjectOptJSONObject);
    }
}
