package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class RequestPushedOperationRequestJson extends RequestJson {
    private static final String NAME_START_OPERATION_REQUEST_TOKEN = "startOperationRequestToken";

    public void setStartOperationRequestToken(String startOperationRequestToken) throws JSONException {
        putPayloadMember(NAME_START_OPERATION_REQUEST_TOKEN, startOperationRequestToken);
    }
}
