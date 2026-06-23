package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ApduCommandInfoJsonArray extends JSONArray {
    private static final String KEY_APDU_COMMAND_LIST = "apduCommandList";
    private static final String KEY_SELECT = "select";

    public ApduCommandInfoJsonArray(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    protected void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        for (int i = 0; i < length(); i++) {
            JSONObject jSONObject = getJSONObject(i);
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject(jSONObject, KEY_SELECT, false);
            if (jSONObjectCheckObject != null && jSONObjectCheckObject.length() != 0) {
                new SelectJson(jSONObjectCheckObject.toString()).checkMembers();
            }
            JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObject, KEY_APDU_COMMAND_LIST, true);
            if (jSONArrayCheckArray != null) {
                new ApduCommandJsonArray(jSONArrayCheckArray.toString()).checkMembers();
            }
        }
        LogMgr.log(8, "999");
    }
}
