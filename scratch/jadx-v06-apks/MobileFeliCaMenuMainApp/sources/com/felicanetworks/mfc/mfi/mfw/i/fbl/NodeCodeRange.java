package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class NodeCodeRange {
    private static final String KEY_LOWER_LIMIT = "lowerLimit";
    private static final String KEY_UPPER_LIMIT = "upperLimit";
    private static final int LEN_NODECODESIZE = 4;
    private long mLowerLimit;
    private long mUpperLimit;

    NodeCodeRange() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    NodeCodeRange(JSONObject jSONObject) throws JSONException {
        LogMgr.log(5, "000 %s", jSONObject);
        try {
            this.mLowerLimit = Long.parseLong(JsonUtil.checkString(jSONObject, KEY_LOWER_LIMIT, true, 4), 16);
            try {
                this.mUpperLimit = Long.parseLong(JsonUtil.checkString(jSONObject, KEY_UPPER_LIMIT, true, 4), 16);
                LogMgr.log(5, "999");
            } catch (NumberFormatException unused) {
                LogMgr.log(2, "%s %s is invalid.", "701", KEY_UPPER_LIMIT);
                throw new JSONException("upperLimit is invalid.");
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(2, "%s %s is invalid.", "700", KEY_LOWER_LIMIT);
            throw new JSONException("lowerLimit is invalid.");
        }
    }

    long getLowerLimit() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mLowerLimit;
    }

    long getUpperLimit() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mUpperLimit;
    }
}
