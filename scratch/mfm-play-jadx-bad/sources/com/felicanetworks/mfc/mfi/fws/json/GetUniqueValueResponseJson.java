package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetUniqueValueResponseJson extends ResponseJson {
    private static final String NAME_UNIQUE_VALUE = "uniqueValue";

    public GetUniqueValueResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        JSONObject jSONObjectCheckObject;
        if (!isSuccess() || (jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", true)) == null) {
            return;
        }
        JsonUtil.checkString(jSONObjectCheckObject, NAME_UNIQUE_VALUE, true, 33);
    }

    public String getUniqueValue() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_UNIQUE_VALUE);
        if (strOptPayloadStringMember != null) {
            return strOptPayloadStringMember;
        }
        throw new JSONException("uniqueValue is null.");
    }
}
