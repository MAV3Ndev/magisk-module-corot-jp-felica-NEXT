package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetPermitListResponseJson extends ResponseJson {
    protected static final String NAME_PERMIT_LIST = "permitList";

    public GetPermitListResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isSuccess()) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", true);
            JsonUtil.checkArray(jSONObjectCheckObject, NAME_PERMIT_LIST, true);
            jSONObjectCheckObject.getJSONArray(NAME_PERMIT_LIST);
        }
    }

    public JSONArray optPermitList() {
        return optPayloadJSONArrayMember(NAME_PERMIT_LIST);
    }
}
