package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class UpdateDeviceRegistrationTokenRequestJson extends RequestJson {
    private static final String NAME_REGISTRATION_REQUEST_TOKEN = "registrationRequestToken";

    public void setRegistrationRequestToken(String registrationRequestToken) throws JSONException {
        putPayloadMember(NAME_REGISTRATION_REQUEST_TOKEN, registrationRequestToken);
    }
}
