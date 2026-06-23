package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetPermitListRequestJson extends RequestJson {
    protected static final String NAME_CONDITIONS = "conditions";
    protected static final String NAME_PLATFORM_TYPE = "platformType";
    protected static final String NAME_SEP_ID = "sepId";
    protected static final String NAME_SE_INFO = "seInfo";
    protected static final String NAME_WALLET_APP_CALLER_INFO_LIST = "walletAppCallerInfoList";
    protected static final String NAME_WALLET_APP_IDENTIFIABLE_INFO = "walletAppIdentifiableInfo";

    public void setPlatformType(String platformType) throws JSONException {
        putConditionsMember(NAME_PLATFORM_TYPE, platformType);
    }

    public void setWalletAppCallerInfoList(JSONArray walletAppCallerInfoList) throws JSONException {
        putConditionsMember(NAME_WALLET_APP_CALLER_INFO_LIST, walletAppCallerInfoList);
    }

    public void setWalletAppIdentifiableInfo(String walletAppIdentifiableInfo) throws JSONException {
        putConditionsMember(NAME_WALLET_APP_IDENTIFIABLE_INFO, walletAppIdentifiableInfo);
    }

    public void setSepId(String sepId) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_SEP_ID, sepId);
        putConditionsMember(NAME_SE_INFO, jSONObject);
    }

    protected void putConditionsMember(String key, JSONArray value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_CONDITIONS);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(key, value);
        put(NAME_CONDITIONS, jSONObjectOptJSONObject);
    }

    protected void putConditionsMember(String key, JSONObject value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_CONDITIONS);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(key, value);
        put(NAME_CONDITIONS, jSONObjectOptJSONObject);
    }

    protected void putConditionsMember(String name, String value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject(NAME_CONDITIONS);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(name, value);
        put(NAME_CONDITIONS, jSONObjectOptJSONObject);
    }
}
