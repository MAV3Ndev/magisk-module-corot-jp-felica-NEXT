package com.felicanetworks.mfc.mfi;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CardInfoWithSpStatusJson extends JSONObject {
    private static final String CARD_INFO_CARD_CATEGORY = "mCardCategory";
    private static final String CARD_INFO_FIELD_ADDITIONAL_INFO_HASH = "mAdditionalInfoHash";
    private static final String CARD_INFO_FIELD_CARD_TYPE = "mCardType";
    private static final String CARD_INFO_FIELD_CID = "mCid";
    private static final String CARD_INFO_FIELD_POSITION = "mPosition";
    private static final String CARD_INFO_FIELD_REISSUE_POSSIBILITY = "mReissuePossibility";
    private static final String CARD_INFO_FIELD_SERVICE_ID = "mServiceId";
    private static final String CARD_INFO_FIELD_SERVICE_TYPE = "mServiceType";
    private static final String CARD_INFO_FIELD_SP_ADDITIONAL_INFO = "mSpAdditionalInfo";
    private static final String CARD_INFO_FIELD_SP_STATUS = "mSpStatus";
    private static final String CARD_INFO_FIELD_STATE = "mState";
    private static final String CARD_INFO_FIELD_TASK = "mTask";
    private static final String CARD_INFO_FIELD_WALLET_APP_ID = "mWalletAppId";

    public CardInfoWithSpStatusJson(String json) throws JSONException {
        super(json);
    }

    public CardInfoWithSpStatusJson(CardInfoWithSpStatus cardInfoWithSpStatus) throws JSONException {
        if (cardInfoWithSpStatus == null) {
            throw new JSONException("CardInfoWithSpStatus is null.");
        }
        put(CARD_INFO_FIELD_CID, cardInfoWithSpStatus.getCid());
        put(CARD_INFO_FIELD_SERVICE_ID, cardInfoWithSpStatus.getServiceId());
        put(CARD_INFO_FIELD_WALLET_APP_ID, cardInfoWithSpStatus.getWalletAppId());
        put(CARD_INFO_FIELD_STATE, cardInfoWithSpStatus.getCardStatus());
        put(CARD_INFO_FIELD_POSITION, cardInfoWithSpStatus.getCardPosition());
        put(CARD_INFO_FIELD_TASK, cardInfoWithSpStatus.getTask());
        put(CARD_INFO_FIELD_REISSUE_POSSIBILITY, cardInfoWithSpStatus.getReissuePossibility());
        put(CARD_INFO_FIELD_SERVICE_TYPE, cardInfoWithSpStatus.getServiceType());
        put(CARD_INFO_FIELD_ADDITIONAL_INFO_HASH, cardInfoWithSpStatus.getAdditionalInfoHash());
        put(CARD_INFO_CARD_CATEGORY, cardInfoWithSpStatus.getCardCategory());
        put(CARD_INFO_FIELD_SP_STATUS, cardInfoWithSpStatus.getSpStatus());
        put(CARD_INFO_FIELD_SP_ADDITIONAL_INFO, cardInfoWithSpStatus.getSpAdditionalInfo());
        put(CARD_INFO_FIELD_CARD_TYPE, cardInfoWithSpStatus.getCardType() == null ? JSONObject.NULL : cardInfoWithSpStatus.getCardType());
    }

    public CardInfoWithSpStatus getCardInfoWithSpStatus() throws JSONException {
        return new CardInfoWithSpStatus(optString(CARD_INFO_FIELD_CID, null), optString(CARD_INFO_FIELD_SERVICE_ID, null), optString(CARD_INFO_FIELD_WALLET_APP_ID, null), getInt(CARD_INFO_FIELD_STATE), getInt(CARD_INFO_FIELD_POSITION), getInt(CARD_INFO_FIELD_TASK), getBoolean(CARD_INFO_FIELD_REISSUE_POSSIBILITY), optString(CARD_INFO_FIELD_SERVICE_TYPE, null), optString(CARD_INFO_FIELD_ADDITIONAL_INFO_HASH, null), optString(CARD_INFO_CARD_CATEGORY, null), getInt(CARD_INFO_FIELD_SP_STATUS), optJSONObject(CARD_INFO_FIELD_SP_ADDITIONAL_INFO), isNull(CARD_INFO_FIELD_CARD_TYPE) ? null : optString(CARD_INFO_FIELD_CARD_TYPE, null));
    }
}
