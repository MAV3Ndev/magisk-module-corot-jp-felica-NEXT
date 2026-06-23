package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class MfcPermit {
    public static final String KEY_COMMAND_CATEGORY = "commandCategory";
    public static final String KEY_OFFLINE_ACCESS_RANGE_LIST = "offlineAccessRangeList";
    private CommandCategory mCommandCategory;
    private List<OfflineAccessRange> mOfflineAccessRangeList;

    MfcPermit() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    MfcPermit(JSONObject jSONObject) throws JSONException {
        LogMgr.log(5, "000 %s", jSONObject);
        this.mCommandCategory = new CommandCategory(JsonUtil.checkObject(jSONObject, KEY_COMMAND_CATEGORY, true));
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObject, KEY_OFFLINE_ACCESS_RANGE_LIST, true);
        this.mOfflineAccessRangeList = new ArrayList(jSONArrayCheckArray.length());
        for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
            this.mOfflineAccessRangeList.add(new OfflineAccessRange(JsonUtil.checkObject(jSONArrayCheckArray, i, true)));
        }
        LogMgr.log(5, "999");
    }

    CommandCategory getCommandCategory() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mCommandCategory;
    }

    List<OfflineAccessRange> getOfflineAccessRangeList() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mOfflineAccessRangeList;
    }
}
