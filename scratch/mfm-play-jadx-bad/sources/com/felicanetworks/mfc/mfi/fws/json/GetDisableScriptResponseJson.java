package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetDisableScriptResponseJson extends GetScriptResponseJson {
    private static final String NAME_DISABLE_CARD = "disabledCard";

    public GetDisableScriptResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptResponseJson, com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        super.checkPayloadMembers();
        if (isSuccess()) {
            JsonUtil.checkObject(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_DISABLE_CARD, true);
        }
    }

    public CardJson getDisableCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_DISABLE_CARD);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("Card is null.");
        }
        return new CardJson(strOptPayloadStringMember);
    }
}
