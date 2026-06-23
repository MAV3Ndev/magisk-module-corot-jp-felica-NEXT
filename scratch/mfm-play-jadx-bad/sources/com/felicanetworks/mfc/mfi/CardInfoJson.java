package com.felicanetworks.mfc.mfi;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CardInfoJson extends JSONObject {
    private static final String CARD_INFO_CARD_CATEGORY = "mCardCategory";
    private static final String CARD_INFO_FIELD_ADDITIONAL_INFO_HASH = "mAdditionalInfoHash";
    private static final String CARD_INFO_FIELD_CARD_TYPE = "mCardType";
    private static final String CARD_INFO_FIELD_CID = "mCid";
    private static final String CARD_INFO_FIELD_POSITION = "mPosition";
    private static final String CARD_INFO_FIELD_REISSUE_POSSIBILITY = "mReissuePossibility";
    private static final String CARD_INFO_FIELD_SERVICE_ID = "mServiceId";
    private static final String CARD_INFO_FIELD_SERVICE_TYPE = "mServiceType";
    private static final String CARD_INFO_FIELD_STATE = "mState";
    private static final String CARD_INFO_FIELD_TASK = "mTask";
    private static final String CARD_INFO_FIELD_WALLET_APP_ID = "mWalletAppId";

    public CardInfoJson(String json) throws JSONException {
        super(json);
    }

    public CardInfoJson(CardInfo cardInfo) throws JSONException {
        if (cardInfo == null) {
            throw new JSONException("cardInfo is null.");
        }
        put(CARD_INFO_FIELD_CID, cardInfo.getCid());
        put(CARD_INFO_FIELD_SERVICE_ID, cardInfo.getServiceId());
        put(CARD_INFO_FIELD_WALLET_APP_ID, cardInfo.getWalletAppId());
        put(CARD_INFO_FIELD_STATE, cardInfo.getCardStatus());
        put(CARD_INFO_FIELD_POSITION, cardInfo.getCardPosition());
        put(CARD_INFO_FIELD_TASK, cardInfo.getTask());
        put(CARD_INFO_FIELD_REISSUE_POSSIBILITY, cardInfo.getReissuePossibility());
        put(CARD_INFO_FIELD_SERVICE_TYPE, cardInfo.getServiceType());
        put(CARD_INFO_FIELD_ADDITIONAL_INFO_HASH, cardInfo.getAdditionalInfoHash());
        put(CARD_INFO_CARD_CATEGORY, cardInfo.getCardCategory());
        put(CARD_INFO_FIELD_CARD_TYPE, cardInfo.getCardType() == null ? JSONObject.NULL : cardInfo.getCardType());
    }

    public CardInfo getCardInfo() throws JSONException {
        return new CardInfo(optString(CARD_INFO_FIELD_CID, null), optString(CARD_INFO_FIELD_SERVICE_ID, null), optString(CARD_INFO_FIELD_WALLET_APP_ID, null), getInt(CARD_INFO_FIELD_STATE), getInt(CARD_INFO_FIELD_POSITION), getInt(CARD_INFO_FIELD_TASK), getBoolean(CARD_INFO_FIELD_REISSUE_POSSIBILITY), optString(CARD_INFO_FIELD_SERVICE_TYPE, null), optString(CARD_INFO_FIELD_ADDITIONAL_INFO_HASH, null), optString(CARD_INFO_CARD_CATEGORY, null), isNull(CARD_INFO_FIELD_CARD_TYPE) ? null : optString(CARD_INFO_FIELD_CARD_TYPE, null));
    }
}
