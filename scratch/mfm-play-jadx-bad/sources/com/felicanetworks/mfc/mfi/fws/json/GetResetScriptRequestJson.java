package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetResetScriptRequestJson extends GetScriptRequestJson {
    protected static final String NAME_SYSTEM_AND_AREA_CODE_LIST = "systemAndAreaCodeList";

    public void setSystemAndAreaCodeList(JSONArray systemAndAreaCodeList) throws JSONException {
        putPayloadMember(NAME_SYSTEM_AND_AREA_CODE_LIST, systemAndAreaCodeList);
    }

    protected void putPayloadMember(String key, JSONArray value) throws JSONException {
        JSONObject jSONObjectOptJSONObject = optJSONObject("payload");
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject();
        }
        jSONObjectOptJSONObject.put(key, value);
        put("payload", jSONObjectOptJSONObject);
    }
}
