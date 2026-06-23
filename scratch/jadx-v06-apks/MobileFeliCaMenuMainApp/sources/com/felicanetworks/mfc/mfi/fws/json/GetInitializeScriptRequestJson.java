package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class GetInitializeScriptRequestJson extends GetScriptRequestJson {
    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptRequestJson
    public void setLinkageData(String str) throws JSONException {
        putPayloadMember("linkageData", str);
    }
}
