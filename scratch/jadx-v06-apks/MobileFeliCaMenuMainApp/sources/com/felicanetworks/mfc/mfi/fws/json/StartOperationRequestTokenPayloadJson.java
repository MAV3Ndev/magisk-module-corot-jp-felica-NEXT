package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class StartOperationRequestTokenPayloadJson extends JSONObject {
    private static final String NAME_MESSAGE_ID = "messageId";
    private static final String NAME_SE_INFO = "seInfo";

    public void setSeInfo(SeInfo seInfo) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, Property.getSeType());
        put(NAME_SE_INFO, seInfoJson);
    }

    public void setMessageId(String str) throws JSONException {
        put(NAME_MESSAGE_ID, str);
    }
}
