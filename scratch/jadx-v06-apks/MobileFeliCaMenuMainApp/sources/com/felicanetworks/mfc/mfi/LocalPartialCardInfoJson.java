package com.felicanetworks.mfc.mfi;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LocalPartialCardInfoJson extends JSONObject {
    private static final String PARTIAL_CARD_INFO_FIELD_CID = "mCid";
    private static final String PARTIAL_CARD_INFO_FIELD_IDM = "mIdm";
    private static final String PARTIAL_CARD_INFO_FIELD_POSITION = "mPosition";
    private static final String PARTIAL_CARD_INFO_FIELD_SERVICE_ID = "mServiceId";

    public LocalPartialCardInfoJson(String str) throws JSONException {
        super(str);
    }

    public LocalPartialCardInfoJson(LocalPartialCardInfo localPartialCardInfo) throws JSONException {
        if (localPartialCardInfo == null) {
            throw new JSONException("localPartialCardInfo is null.");
        }
        if (localPartialCardInfo.getIDm() == null) {
            throw new JSONException("idm is null.");
        }
        if (localPartialCardInfo.getServiceId() == null) {
            throw new JSONException("serviceId is null.");
        }
        put(PARTIAL_CARD_INFO_FIELD_CID, localPartialCardInfo.getCid());
        put(PARTIAL_CARD_INFO_FIELD_IDM, localPartialCardInfo.getIDm());
        put(PARTIAL_CARD_INFO_FIELD_POSITION, localPartialCardInfo.getCardPosition());
        put(PARTIAL_CARD_INFO_FIELD_SERVICE_ID, localPartialCardInfo.getServiceId());
    }

    public LocalPartialCardInfo getLocalPartialCardInfo() throws JSONException {
        return new LocalPartialCardInfo(optString(PARTIAL_CARD_INFO_FIELD_CID, null), getString(PARTIAL_CARD_INFO_FIELD_IDM), getInt(PARTIAL_CARD_INFO_FIELD_POSITION), getString(PARTIAL_CARD_INFO_FIELD_SERVICE_ID));
    }
}
