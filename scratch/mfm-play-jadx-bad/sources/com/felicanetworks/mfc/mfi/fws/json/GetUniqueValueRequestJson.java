package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetUniqueValueRequestJson extends RequestJson {
    protected static final String NAME_UNIQUE_VALUE_TOKEN = "uniqueValueRequestToken";

    public void setUniqueValueRequestToken(String uniqueValueRequestToken) throws JSONException {
        putPayloadMember(NAME_UNIQUE_VALUE_TOKEN, uniqueValueRequestToken);
    }
}
