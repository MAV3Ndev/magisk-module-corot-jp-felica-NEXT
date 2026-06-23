package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class LinkageDataRequestJson extends GetScriptRequestJson {
    private static final String NAME_LINKAGE_DATA_LIST_REQUEST_TOKEN = "linkageDataListRequestToken";

    public void setLinkageDataListRequestToken(String str) throws JSONException {
        putPayloadMember(NAME_LINKAGE_DATA_LIST_REQUEST_TOKEN, str);
    }
}
