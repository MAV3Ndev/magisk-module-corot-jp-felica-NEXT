package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class RequestPushedOperationRequestJson extends RequestJson {
    private static final String NAME_START_OPERATION_REQUEST_TOKEN = "startOperationRequestToken";

    public void setStartOperationRequestToken(String str) throws JSONException {
        putPayloadMember(NAME_START_OPERATION_REQUEST_TOKEN, str);
    }
}
