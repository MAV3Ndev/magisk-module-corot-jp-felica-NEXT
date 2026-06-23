package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class OfflineAccessRange {
    private static final String KEY_NODE_CODE_RANGE_LIST = "nodeCodeRangeList";
    private static final String KEY_SYSTEM_CODE = "systemCode";
    private static final int LEN_SYSTEM_CODE = 4;
    private List<NodeCodeRange> mNodeCodeRangeList;
    private int mSystemCode;

    OfflineAccessRange() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    OfflineAccessRange(JSONObject jSONObject) throws JSONException {
        LogMgr.log(5, "000 %s", jSONObject);
        try {
            this.mSystemCode = StringUtil.hexToInteger(JsonUtil.checkString(jSONObject, KEY_SYSTEM_CODE, true, 4));
            JSONArray jSONArray = jSONObject.getJSONArray(KEY_NODE_CODE_RANGE_LIST);
            this.mNodeCodeRangeList = new ArrayList(jSONArray.length());
            for (int i = 0; i < jSONArray.length(); i++) {
                this.mNodeCodeRangeList.add(new NodeCodeRange(JsonUtil.checkObject(jSONArray, i, true)));
            }
            LogMgr.log(5, "999");
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "%s System code is invalid.", "700");
            throw new JSONException("System code is invalid.");
        }
    }

    int getSystemCode() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mSystemCode;
    }

    List<NodeCodeRange> getNodeCodeRangeList() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mNodeCodeRangeList;
    }
}
