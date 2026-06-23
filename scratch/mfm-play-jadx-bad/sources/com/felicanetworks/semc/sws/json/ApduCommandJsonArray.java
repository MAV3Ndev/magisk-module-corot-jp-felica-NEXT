package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ApduCommandJsonArray extends JSONArray {
    private static final String KEY_APDU_COMMAND = "apduCommand";
    private static final String KEY_APDU_ID = "apduId";
    private static final String KEY_APDU_NAME = "apduName";

    public ApduCommandJsonArray(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    protected void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        for (int i = 0; i < length(); i++) {
            JSONObject jSONObject = getJSONObject(i);
            JsonUtil.checkString(jSONObject, KEY_APDU_ID, true, 32);
            JsonUtil.checkString(jSONObject, KEY_APDU_NAME, false, 0, 255);
            JsonUtil.checkString(jSONObject, KEY_APDU_COMMAND, true, 10, JsonConst.LEN_APDU_COMMAND_MAX);
        }
        LogMgr.log(8, "999");
    }
}
