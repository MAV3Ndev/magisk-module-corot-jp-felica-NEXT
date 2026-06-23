package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CheckExistsManagementCardResponseJson extends ResponseJson {
    private static final String NAME_EXISTS = "exists";

    public CheckExistsManagementCardResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isSuccess()) {
            JsonUtil.checkBoolean(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_EXISTS, true);
        }
    }

    public Boolean getExists() throws JSONException {
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", false);
        if (jSONObjectCheckObject == null) {
            throw new JSONException("payload is null.");
        }
        return Boolean.valueOf(jSONObjectCheckObject.getBoolean(NAME_EXISTS));
    }
}
