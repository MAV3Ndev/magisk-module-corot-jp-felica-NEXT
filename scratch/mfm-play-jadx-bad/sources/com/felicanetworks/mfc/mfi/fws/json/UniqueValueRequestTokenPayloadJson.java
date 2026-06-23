package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class UniqueValueRequestTokenPayloadJson extends JSONObject {
    private static final String NAME_BUSINESS_LOGIC_DATA = "businessLogicData";
    private static final String NAME_CNONCE = "cnonce";
    private static final String NAME_SE_INFO = "seInfo";

    public void setClientNonce(String clientNonce) throws JSONException {
        put(NAME_CNONCE, clientNonce);
    }

    public void setSeInfo(SeInfo seInfo) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, Property.getSeType());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_SE_INFO, seInfoJson);
        put(NAME_BUSINESS_LOGIC_DATA, jSONObject);
    }
}
