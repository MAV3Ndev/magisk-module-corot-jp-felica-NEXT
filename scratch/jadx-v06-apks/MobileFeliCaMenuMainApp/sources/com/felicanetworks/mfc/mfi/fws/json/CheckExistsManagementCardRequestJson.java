package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class CheckExistsManagementCardRequestJson extends RequestJson {
    private static final String NAME_CHECK_EXISTS_MANAGEMENT_CARD_TOKEN = "checkExistsManagementCardToken";

    public void setCheckExistsManagementCardToken(String str) throws JSONException {
        putPayloadMember(NAME_CHECK_EXISTS_MANAGEMENT_CARD_TOKEN, str);
    }
}
