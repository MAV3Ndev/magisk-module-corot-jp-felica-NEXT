package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CreateCardResponseJson extends GetScriptResponseJson {
    private static final String NAME_CREATED_CARD = "createdCard";

    public CreateCardResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptResponseJson, com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        super.checkPayloadMembers();
        if (isContinue()) {
            JsonUtil.checkObject((JSONObject) this, "payload", true);
        }
    }

    public CardJson getCreatedCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_CREATED_CARD);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("Card is null.");
        }
        return new CardJson(strOptPayloadStringMember);
    }
}
