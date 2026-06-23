package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetCardListResponseJson extends ResponseJson {
    private static final String NAME_CARD_IDENTIFIABLE_INFO_LIST = "cardIdentifiableInfoList";
    private static final String NAME_CARD_LIST = "cardList";

    public GetCardListResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isSuccess()) {
            JsonUtil.checkObject((JSONObject) this, "payload", true);
        }
    }

    public List<CardJson> getCardList() throws JSONException {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_CARD_LIST, true);
        int length = jSONArrayCheckArray.length();
        for (int i = 0; i < length; i++) {
            try {
                String string = jSONArrayCheckArray.getString(i);
                if (string == null) {
                    throw new JSONException("Card info is null. index=" + i);
                }
                arrayList.add(new CardJson(string));
            } catch (JSONException e) {
                LogMgr.log(2, "%s Fail to parse card info json. index=%d", "700", Integer.valueOf(i));
                throw e;
            }
        }
        return arrayList;
    }

    public List<CardIdentifiableInfoJson> getCardIdentifiableInfoList() throws JSONException {
        JsonUtil.checkArray(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_CARD_IDENTIFIABLE_INFO_LIST, true);
        String strOptPayloadStringMember = optPayloadStringMember(NAME_CARD_IDENTIFIABLE_INFO_LIST);
        if (strOptPayloadStringMember != null) {
            return new CardIdentifiableInfoJsonArray(strOptPayloadStringMember).getCardIdentifiableInfoList();
        }
        return new ArrayList();
    }
}
