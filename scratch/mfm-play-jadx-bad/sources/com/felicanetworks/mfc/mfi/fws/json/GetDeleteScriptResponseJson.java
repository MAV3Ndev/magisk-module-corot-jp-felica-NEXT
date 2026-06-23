package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetDeleteScriptResponseJson extends GetScriptResponseJson {
    private static final String NAME_CARD_IDENTIFIABLE_INFO_LIST = "cardIdentifiableInfoList";
    private static final String NAME_DELETED_CARD = "deletedCard";

    public GetDeleteScriptResponseJson(String json) throws JSONException {
        super(json);
    }

    public CardJson getDeletedCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_DELETED_CARD);
        if (strOptPayloadStringMember == null) {
            LogMgr.log(2, "Not contain deletedCard in response.");
            return null;
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
