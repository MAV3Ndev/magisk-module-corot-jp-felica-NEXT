package com.felicanetworks.mfc.mfi.fws.json;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GetIssueScriptResponseJson extends GetScriptResponseJson {
    private static final String NAME_CARD_IDENTIFIABLE_INFO_LIST = "cardIdentifiableInfoList";
    private static final String NAME_ISSUE_CARD = "issuedCard";

    public GetIssueScriptResponseJson(String str) throws JSONException {
        super(str);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptResponseJson, com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        super.checkPayloadMembers();
        if (isSuccess()) {
            JsonUtil.checkObject(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_ISSUE_CARD, true);
        }
    }

    public CardJson getIssueCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_ISSUE_CARD);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("Card is null.");
        }
        return new CardJson(strOptPayloadStringMember);
    }

    public List<CardIdentifiableInfoJson> getCardIdentifiableInfoList() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_CARD_IDENTIFIABLE_INFO_LIST);
        if (strOptPayloadStringMember != null) {
            return new CardIdentifiableInfoJsonArray(strOptPayloadStringMember).getCardIdentifiableInfoList();
        }
        return new ArrayList();
    }
}
