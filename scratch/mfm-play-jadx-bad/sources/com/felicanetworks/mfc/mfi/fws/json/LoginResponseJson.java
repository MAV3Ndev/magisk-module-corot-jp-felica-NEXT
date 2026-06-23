package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class LoginResponseJson extends ResponseJson {
    private static final String NAME_LOGIN_TOKEN = "loginToken";
    private static final String NAME_REMAINED_CARD_IDENTIFIABLE_INFO_LIST = "remainedCardIdentifiableInfoList";
    private static final String NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO = "remainedDeleteCardIdentifiableInfo";
    private static final String NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO_LIST = "remainedDeleteCardIdentifiableInfoList";
    private static final String NAME_REMAINED_DELETE_SIMPLE_CARD_INFO_LIST = "remainedDeleteSimpleCardInfoList";

    public LoginResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isSuccess()) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", true);
            JsonUtil.checkString(jSONObjectCheckObject, NAME_LOGIN_TOKEN, true, 0);
            JsonUtil.checkArray(jSONObjectCheckObject, NAME_REMAINED_CARD_IDENTIFIABLE_INFO_LIST, true);
            JsonUtil.checkObject(jSONObjectCheckObject, NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO, true);
            JsonUtil.checkArray(optRemainedDeleteCardIdentifiableInfo(), NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO_LIST, true);
            JsonUtil.checkArray(optRemainedDeleteCardIdentifiableInfo(), NAME_REMAINED_DELETE_SIMPLE_CARD_INFO_LIST, true);
        }
    }

    public String optLoginToken() {
        return optPayloadStringMember(NAME_LOGIN_TOKEN);
    }

    public JSONArray optRemainedCardIdentifiableInfoList() {
        return optPayloadJSONArrayMember(NAME_REMAINED_CARD_IDENTIFIABLE_INFO_LIST);
    }

    public JSONArray optRemainedDeleteCardIdentifiableInfoList() {
        return optRemainedDeleteCardIdentifiableInfo().optJSONArray(NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO_LIST);
    }

    public JSONArray optRemainedDeleteSimpleCardInfoList() {
        return optRemainedDeleteCardIdentifiableInfo().optJSONArray(NAME_REMAINED_DELETE_SIMPLE_CARD_INFO_LIST);
    }

    private JSONObject optRemainedDeleteCardIdentifiableInfo() {
        return optPayloadJSONObjectMember(NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO);
    }
}
