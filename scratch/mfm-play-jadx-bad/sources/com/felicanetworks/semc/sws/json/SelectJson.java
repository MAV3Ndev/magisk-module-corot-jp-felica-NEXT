package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SelectJson extends JSONObject {
    private static final String KEY_AID = "aid";
    private static final String KEY_P2 = "p2";

    public SelectJson(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    protected void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        JsonUtil.checkString(this, KEY_AID, true, 10, 32);
        JsonUtil.checkString(this, KEY_P2, false, 2);
        LogMgr.log(8, "999");
    }
}
