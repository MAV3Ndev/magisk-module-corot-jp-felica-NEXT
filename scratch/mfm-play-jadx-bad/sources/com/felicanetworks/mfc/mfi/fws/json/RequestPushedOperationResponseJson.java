package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.fws.FwsConst;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class RequestPushedOperationResponseJson extends ResponseJson {
    private static final String NAME_ACCESSED_CARD = "accessedCard";
    private static final String NAME_CARD_IDENTIFIABLE_INFO = "cardIdentifiableInfo";
    private static final String NAME_LINKAGE_DATA = "linkageData";

    public RequestPushedOperationResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        if (isSuccess()) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, "payload", true);
            JsonUtil.checkObject(jSONObjectCheckObject, NAME_ACCESSED_CARD, true);
            JsonUtil.checkObject(jSONObjectCheckObject, NAME_CARD_IDENTIFIABLE_INFO, true);
            JsonUtil.checkString(jSONObjectCheckObject, "linkageData", true, 0);
        }
    }

    public String getActionType() throws JSONException {
        String actionType = null;
        try {
            String strOptLinkageData = optLinkageData();
            if (strOptLinkageData != null) {
                actionType = new LinkageDataJson(new JwsObject(strOptLinkageData).getPayload()).getActionType();
            }
        } catch (JSONException unused) {
        }
        if (FwsConst.ActionType.DELETE_CARDS_FOR_PUSH.equals(actionType) || FwsConst.ActionType.SE_ACCESS_FOR_PUSH.equals(actionType)) {
            return actionType;
        }
        throw new JSONException("ActionType is invalid. :" + actionType);
    }

    public String optLinkageData() {
        return optPayloadStringMember("linkageData");
    }

    public CardJson getAccessedCard() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_ACCESSED_CARD);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("Card is null.");
        }
        return new CardJson(strOptPayloadStringMember);
    }

    public CardIdentifiableInfoJson getCardIdentifiableInfo() throws JSONException {
        String strOptPayloadStringMember = optPayloadStringMember(NAME_CARD_IDENTIFIABLE_INFO);
        if (strOptPayloadStringMember == null) {
            throw new JSONException("CardIdentifiableInfo is null.");
        }
        return new CardIdentifiableInfoJson(strOptPayloadStringMember);
    }
}
