package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GetAccessScriptResponseJson extends GetScriptResponseJson {
    private static final String NAME_ACCESSED_CARD = "accessedCard";

    public GetAccessScriptResponseJson(String str) throws JSONException {
        super(str);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptResponseJson, com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        super.checkPayloadMembers();
        if (isSuccess()) {
            JsonUtil.checkObject(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_ACCESSED_CARD, true);
        }
    }

    public CardJson getAccessCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_ACCESSED_CARD);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("Card is null.");
        }
        return new CardJson(strOptPayloadStringMember);
    }
}
