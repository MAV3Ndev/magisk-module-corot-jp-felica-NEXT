package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class GetAccessScriptRequestJson extends GetScriptRequestJson {
    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptRequestJson
    public void setLinkageData(String str) throws JSONException {
        putPayloadMember("linkageData", str);
    }

    public void setSequenceCounter(String str) throws JSONException {
        putPayloadMember("sequenceCounter", str);
    }
}
