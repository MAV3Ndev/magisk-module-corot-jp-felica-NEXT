package com.felicanetworks.mfc.mfi;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CardAdditionalInfoJson extends JSONObject {
    private static final String CARD_ADDITIONAL_INFO_ADDITIONAL_INFO = "mAdditionalInfo";
    private static final String CARD_ADDITIONAL_INFO_ADDITIONAL_INFO_HASH = "mAdditionalInfoHash";
    private static final String CARD_ADDITIONAL_INFO_FIELD_CARD_TYPE = "mCardType";
    private static final String CARD_ADDITIONAL_INFO_FIELD_CID = "mCid";
    private static final String CARD_ADDITIONAL_INFO_ISSUER_ID = "mIssuerId";

    public CardAdditionalInfoJson(String json) throws JSONException {
        super(json);
    }

    public CardAdditionalInfoJson(CardAdditionalInfo cardAdditionalInfo) throws JSONException {
        if (cardAdditionalInfo == null) {
            throw new JSONException("cardAdditionalInfo is null.");
        }
        put(CARD_ADDITIONAL_INFO_FIELD_CID, cardAdditionalInfo.getCid());
        put(CARD_ADDITIONAL_INFO_FIELD_CARD_TYPE, cardAdditionalInfo.getCardType());
        put(CARD_ADDITIONAL_INFO_ISSUER_ID, cardAdditionalInfo.getIssuerId());
        put(CARD_ADDITIONAL_INFO_ADDITIONAL_INFO, cardAdditionalInfo.getAdditionalInfo());
        put(CARD_ADDITIONAL_INFO_ADDITIONAL_INFO_HASH, cardAdditionalInfo.getAdditionalInfoHash());
    }

    public CardAdditionalInfo getCardAdditionalInfo() {
        return new CardAdditionalInfo(optString(CARD_ADDITIONAL_INFO_FIELD_CID, null), optString(CARD_ADDITIONAL_INFO_FIELD_CARD_TYPE, null), optString(CARD_ADDITIONAL_INFO_ISSUER_ID, null), optJSONObject(CARD_ADDITIONAL_INFO_ADDITIONAL_INFO), optString(CARD_ADDITIONAL_INFO_ADDITIONAL_INFO_HASH, null));
    }
}
