package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class NotifyDeviceMessageRequestJson extends RequestJson {
    private static final String NAME_DEVICE_MESSAGE_TOKEN = "deviceMessageToken";

    public void setDeviceMessageToken(String str) throws JSONException {
        putPayloadMember(NAME_DEVICE_MESSAGE_TOKEN, str);
    }
}
