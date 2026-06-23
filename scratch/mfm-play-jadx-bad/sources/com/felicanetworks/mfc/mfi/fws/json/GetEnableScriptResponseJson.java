package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetEnableScriptResponseJson extends GetScriptResponseJson {
    private static final String NAME_DISABLE_CARD = "disabledCard";
    private static final String NAME_ENABLE_CARD = "enabledCard";

    public GetEnableScriptResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptResponseJson, com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        super.checkPayloadMembers();
        if (isSuccess()) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", true);
            JsonUtil.checkObject(jSONObjectCheckObject, NAME_ENABLE_CARD, true);
            JsonUtil.checkObject(jSONObjectCheckObject, NAME_DISABLE_CARD, false);
        }
    }

    public CardJson getEnableCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_ENABLE_CARD);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("Card is null.");
        }
        return new CardJson(strOptPayloadStringMember);
    }

    public boolean hasDisableCard() {
        return hasPayloadMember(NAME_DISABLE_CARD);
    }

    public CardJson getDisableCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_DISABLE_CARD);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("Card is null.");
        }
        return new CardJson(strOptPayloadStringMember);
    }
}
