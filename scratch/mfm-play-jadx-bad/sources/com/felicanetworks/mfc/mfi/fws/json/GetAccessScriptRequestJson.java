package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.semc.fcm.CloudMessagingWorker;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetAccessScriptRequestJson extends GetScriptRequestJson {
    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptRequestJson
    public void setLinkageData(String linkageData) throws JSONException {
        putPayloadMember(CloudMessagingWorker.EXT_KEY_LINKAGE_DATA, linkageData);
    }

    public void setSequenceCounter(String sequenceCounter) throws JSONException {
        putPayloadMember("sequenceCounter", sequenceCounter);
    }
}
