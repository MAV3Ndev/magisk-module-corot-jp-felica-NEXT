package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class PermitCache extends JSONObject {
    protected static final String KEY_PERMIT = "permit";
    protected static final String KEY_USAGE_COUNT = "usageCount";

    public PermitCache() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    public PermitCache(String json) throws JSONException {
        super(json);
    }

    public String getPermit() throws JSONException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return getString(KEY_PERMIT);
    }

    public void putPermit(String permit) throws JSONException {
        put(KEY_PERMIT, permit);
    }

    public int getUsageCount() throws JSONException {
        return getInt(KEY_USAGE_COUNT);
    }

    public void putUsageCount(int usageCount) throws JSONException {
        put(KEY_USAGE_COUNT, usageCount);
    }
}
