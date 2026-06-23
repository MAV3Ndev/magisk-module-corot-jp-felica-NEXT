package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SpAppletInfoListJsonArray extends JSONArray {
    private static final String KEY_SERVICE_ID = "serviceId";
    private static final String KEY_SP_APPLET_ID = "spAppletId";
    private static final String KEY_SP_APPLET_VERSION = "spAppletVersion";

    public SpAppletInfoListJsonArray(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    protected void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        for (int i = 0; i < length(); i++) {
            JSONObject jSONObject = getJSONObject(i);
            JsonUtil.checkString(jSONObject, "serviceId", true, 8);
            JsonUtil.checkString(jSONObject, "spAppletId", true, 8);
            JsonUtil.checkString(jSONObject, "spAppletVersion", true, 4);
        }
        LogMgr.log(8, "999");
    }
}
