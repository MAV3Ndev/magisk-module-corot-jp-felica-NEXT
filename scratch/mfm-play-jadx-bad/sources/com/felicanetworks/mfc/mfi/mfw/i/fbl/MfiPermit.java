package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
class MfiPermit {
    private static final String KEY_FUNCTION_CATEGORY = "functionCategory";
    private static final String KEY_GET_CARD_LIST = "getCardList";
    private static final String KEY_OFFLINE_ACCESS_SERVICE_ID_LIST = "offlineAccessServiceIdList";
    private static final String KEY_START = "start";
    private FunctionCategory mFunctionCategory;
    private GetCardList mGetCardList;
    private List<OfflineAccessServiceId> mOfflineAccessServiceIdList;
    private Start mStart;

    MfiPermit() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    MfiPermit(JSONObject jsonObject) throws JSONException {
        LogMgr.log(5, "000 %s", jsonObject);
        this.mStart = new Start(JsonUtil.checkObject(jsonObject, "start", true));
        this.mFunctionCategory = new FunctionCategory(JsonUtil.checkObject(jsonObject, KEY_FUNCTION_CATEGORY, true), this.mStart.isAdminStart());
        this.mGetCardList = new GetCardList(JsonUtil.checkObject(jsonObject, KEY_GET_CARD_LIST, true));
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jsonObject, KEY_OFFLINE_ACCESS_SERVICE_ID_LIST, false);
        int length = jSONArrayCheckArray == null ? 0 : jSONArrayCheckArray.length();
        this.mOfflineAccessServiceIdList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            this.mOfflineAccessServiceIdList.add(new OfflineAccessServiceId(JsonUtil.checkObject(jSONArrayCheckArray, i, true)));
        }
        LogMgr.log(5, "999");
    }

    FunctionCategory getFunctionCategory() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mFunctionCategory;
    }

    Start getStart() {
        return this.mStart;
    }

    GetCardList getGetCardList() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mGetCardList;
    }

    List<OfflineAccessServiceId> getOfflineAccessServiceIdList() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mOfflineAccessServiceIdList;
    }
}
