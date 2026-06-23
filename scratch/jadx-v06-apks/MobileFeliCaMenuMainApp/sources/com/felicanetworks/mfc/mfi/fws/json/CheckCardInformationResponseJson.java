package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CheckCardInformationResponseJson extends ResponseJson {
    private static final String NAME_LINKAGE_DATA = "linkageData";

    public CheckCardInformationResponseJson(String str) throws JSONException {
        super(str);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isContinue()) {
            JsonUtil.checkString(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_LINKAGE_DATA, true, 0);
        }
    }

    public String getLinakgeData() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_LINKAGE_DATA);
        if (strOptPayloadStringMember != null) {
            return strOptPayloadStringMember;
        }
        throw new JSONException("linkageData is null.");
    }
}
