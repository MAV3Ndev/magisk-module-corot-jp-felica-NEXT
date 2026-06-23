package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageDataRequestJson extends GetScriptRequestJson {
    private static final String NAME_LINKAGE_DATA_LIST_REQUEST_TOKEN = "linkageDataListRequestToken";

    public void setLinkageDataListRequestToken(String linkageDataListRequestToken) throws JSONException {
        putPayloadMember(NAME_LINKAGE_DATA_LIST_REQUEST_TOKEN, linkageDataListRequestToken);
    }

    public void setIntegrityInfo(String uniqueValue, String walletAppIdentifiableInfo, String integrityApiToken) throws JSONException {
        IntegrityInfoJson integrityInfoJson = new IntegrityInfoJson();
        integrityInfoJson.setUniqueValue(uniqueValue);
        integrityInfoJson.setWalletAppIdentifiableInfo(walletAppIdentifiableInfo);
        integrityInfoJson.setIntegrityApiToken(integrityApiToken);
        putPayloadMember(integrityInfoJson.getName(), integrityInfoJson);
    }
}
