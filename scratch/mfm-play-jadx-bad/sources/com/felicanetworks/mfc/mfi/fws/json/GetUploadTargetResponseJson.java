package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetUploadTargetResponseJson extends ResponseJson {
    private static final String NAME_REMAINED_CARD_IDENTIFIABLE_INFO = "remainedCardIdentifiableInfo";
    private static final String NAME_REMAINED_CARD_IDENTIFIABLE_INFO_LIST = "remainedCardIdentifiableInfoList";
    private static final String NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO = "remainedDeleteCardIdentifiableInfo";
    private static final String NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO_LIST = "remainedDeleteCardIdentifiableInfoList";
    private static final String NAME_REMAINED_DELETE_SIMPLE_CARD_INFO_LIST = "remainedDeleteSimpleCardInfoList";
    private static final String NAME_REMAINED_SIMPLE_CARD_INFO_LIST = "remainedSimpleCardInfoList";

    public GetUploadTargetResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isSuccess()) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", true);
            if (jSONObjectCheckObject != null) {
                JsonUtil.checkObject(jSONObjectCheckObject, NAME_REMAINED_CARD_IDENTIFIABLE_INFO, true);
                JsonUtil.checkArray(optRemainedCardIdentifiableInfo(), NAME_REMAINED_CARD_IDENTIFIABLE_INFO_LIST, true);
                JsonUtil.checkArray(optRemainedCardIdentifiableInfo(), NAME_REMAINED_SIMPLE_CARD_INFO_LIST, true);
                JsonUtil.checkObject(jSONObjectCheckObject, NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO, true);
                JsonUtil.checkArray(optRemainedDeleteCardIdentifiableInfo(), NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO_LIST, true);
                JsonUtil.checkArray(optRemainedDeleteCardIdentifiableInfo(), NAME_REMAINED_DELETE_SIMPLE_CARD_INFO_LIST, true);
                return;
            }
            throw new JSONException("payload is null");
        }
    }

    private JSONObject optRemainedCardIdentifiableInfo() {
        return optPayloadJSONObjectMember(NAME_REMAINED_CARD_IDENTIFIABLE_INFO);
    }

    public JSONArray optRemainedCardIdentifiableInfoList() {
        return optRemainedCardIdentifiableInfo().optJSONArray(NAME_REMAINED_CARD_IDENTIFIABLE_INFO_LIST);
    }

    public JSONArray optRemainedSimpleCardInfoList() {
        return optRemainedCardIdentifiableInfo().optJSONArray(NAME_REMAINED_SIMPLE_CARD_INFO_LIST);
    }

    private JSONObject optRemainedDeleteCardIdentifiableInfo() {
        return optPayloadJSONObjectMember(NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO);
    }

    public JSONArray optRemainedDeleteCardIdentifiableInfoList() {
        return optRemainedDeleteCardIdentifiableInfo().optJSONArray(NAME_REMAINED_DELETE_CARD_IDENTIFIABLE_INFO_LIST);
    }

    public JSONArray optRemainedDeleteSimpleCardInfoList() {
        return optRemainedDeleteCardIdentifiableInfo().optJSONArray(NAME_REMAINED_DELETE_SIMPLE_CARD_INFO_LIST);
    }
}
