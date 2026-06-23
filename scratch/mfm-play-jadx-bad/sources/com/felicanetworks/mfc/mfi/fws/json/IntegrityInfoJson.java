package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class IntegrityInfoJson extends JSONObject implements IJsonMember {
    private static final String NAME = "integrityInfo";
    private static final String NAME_INTEGRITY_API_TOKEN = "integrityApiToken";
    private static final String NAME_UNIQUE_VALUE = "uniqueValue";
    private static final String NAME_WALLET_APP_IDENTIFIABLE_INFO = "walletAppIdentifiableInfo";

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setUniqueValue(String uniqueValue) throws JSONException {
        put(NAME_UNIQUE_VALUE, uniqueValue);
    }

    public void setWalletAppIdentifiableInfo(String walletAppIdentifiableInfo) throws JSONException {
        put(NAME_WALLET_APP_IDENTIFIABLE_INFO, walletAppIdentifiableInfo);
    }

    public void setIntegrityApiToken(String integrityApiToken) throws JSONException {
        put(NAME_INTEGRITY_API_TOKEN, integrityApiToken);
    }
}
