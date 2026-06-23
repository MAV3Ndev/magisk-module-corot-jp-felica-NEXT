package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class DivideParameterJson extends JSONObject implements IJsonMember {
    private static final String NAME = "divideParameter";
    private static final String NAME_CARRIER_ID = "carrierId";
    private static final String NAME_DEVICE_ID = "deviceId";

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setDivideParameter(String carrierId, String deviceId) throws JSONException {
        if (carrierId == null || deviceId == null) {
            return;
        }
        put(NAME_CARRIER_ID, carrierId);
        put(NAME_DEVICE_ID, deviceId);
    }
}
