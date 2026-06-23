package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetUploadTargetRequestJson extends RequestJson {
    protected static final String NAME_UPLOAD_TARGET_TOKEN = "uploadTargetRequestToken";

    public void setUploadTargetRequestToken(String uploadTargetRequestToken) throws JSONException {
        putPayloadMember(NAME_UPLOAD_TARGET_TOKEN, uploadTargetRequestToken);
    }
}
