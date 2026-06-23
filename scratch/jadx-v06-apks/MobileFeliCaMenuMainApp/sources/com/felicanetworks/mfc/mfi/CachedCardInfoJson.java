package com.felicanetworks.mfc.mfi;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CachedCardInfoJson extends JSONObject {
    private static final String CARD_INFO_CARD_CATEGORY = "mCardCategory";
    private static final String CARD_INFO_FIELD_ADDITIONAL_INFO_HASH = "mAdditionalInfoHash";
    private static final String CARD_INFO_FIELD_CACHED_TIME = "mCachedTime";
    private static final String CARD_INFO_FIELD_CID = "mCid";
    private static final String CARD_INFO_FIELD_POSITION = "mPosition";
    private static final String CARD_INFO_FIELD_REISSUE_POSSIBILITY = "mReissuePossibility";
    private static final String CARD_INFO_FIELD_SERVICE_ID = "mServiceId";
    private static final String CARD_INFO_FIELD_SERVICE_TYPE = "mServiceType";
    private static final String CARD_INFO_FIELD_STATE = "mState";
    private static final String CARD_INFO_FIELD_TASK = "mTask";
    private static final String CARD_INFO_FIELD_WALLET_APP_ID = "mWalletAppId";

    public CachedCardInfoJson(String str) throws JSONException {
        super(str);
    }

    public CachedCardInfoJson(CachedCardInfo cachedCardInfo) throws JSONException {
        if (cachedCardInfo == null) {
            throw new JSONException("cachedCardInfo is null.");
        }
        put(CARD_INFO_FIELD_CID, cachedCardInfo.getCid());
        put(CARD_INFO_FIELD_SERVICE_ID, cachedCardInfo.getServiceId());
        put(CARD_INFO_FIELD_WALLET_APP_ID, cachedCardInfo.getWalletAppId());
        put(CARD_INFO_FIELD_STATE, cachedCardInfo.getCardStatus());
        put(CARD_INFO_FIELD_POSITION, cachedCardInfo.getCardPosition());
        put(CARD_INFO_FIELD_TASK, cachedCardInfo.getTask());
        put(CARD_INFO_FIELD_REISSUE_POSSIBILITY, cachedCardInfo.getReissuePossibility());
        put(CARD_INFO_FIELD_SERVICE_TYPE, cachedCardInfo.getServiceType());
        put(CARD_INFO_FIELD_ADDITIONAL_INFO_HASH, cachedCardInfo.getAdditionalInfoHash());
        put(CARD_INFO_CARD_CATEGORY, cachedCardInfo.getCardCategory());
        put(CARD_INFO_FIELD_CACHED_TIME, cachedCardInfo.getCachedTime());
    }

    public CachedCardInfo getCachedCardInfo() throws JSONException {
        return new CachedCardInfo(optString(CARD_INFO_FIELD_CID, null), optString(CARD_INFO_FIELD_SERVICE_ID, null), optString(CARD_INFO_FIELD_WALLET_APP_ID, null), getInt(CARD_INFO_FIELD_STATE), getInt(CARD_INFO_FIELD_POSITION), getInt(CARD_INFO_FIELD_TASK), getBoolean(CARD_INFO_FIELD_REISSUE_POSSIBILITY), optString(CARD_INFO_FIELD_SERVICE_TYPE, null), optString(CARD_INFO_FIELD_ADDITIONAL_INFO_HASH, null), optString(CARD_INFO_CARD_CATEGORY, null), optLong(CARD_INFO_FIELD_CACHED_TIME));
    }
}
