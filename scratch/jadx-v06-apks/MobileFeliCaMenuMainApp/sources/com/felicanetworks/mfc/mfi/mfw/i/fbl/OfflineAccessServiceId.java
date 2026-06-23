package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class OfflineAccessServiceId {
    private static final String KEY_SERVICE_ID = "serviceId";
    private static final int LEN_SERVICE_ID = 8;
    private String mServiceId;

    OfflineAccessServiceId() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    OfflineAccessServiceId(JSONObject jSONObject) throws JSONException {
        LogMgr.log(5, "000 : " + jSONObject);
        this.mServiceId = JsonUtil.checkString(jSONObject, KEY_SERVICE_ID, true, 8);
        LogMgr.log(5, "999");
    }

    String getServiceId() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mServiceId;
    }
}
