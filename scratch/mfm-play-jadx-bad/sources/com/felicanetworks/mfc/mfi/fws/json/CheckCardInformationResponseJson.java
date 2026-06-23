package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CheckCardInformationResponseJson extends ResponseJson {
    private static final String NAME_LINKAGE_DATA = "linkageData";

    public CheckCardInformationResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isContinue()) {
            JsonUtil.checkString(JsonUtil.checkObject((JSONObject) this, "payload", true), "linkageData", true, 0);
        }
    }

    public String getLinakgeData() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember("linkageData");
        if (strOptPayloadStringMember != null) {
            return strOptPayloadStringMember;
        }
        throw new JSONException("linkageData is null.");
    }
}
