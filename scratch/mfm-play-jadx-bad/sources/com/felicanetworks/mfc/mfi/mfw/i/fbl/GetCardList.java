package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
class GetCardList {
    private static final String KEY_ADDITIONAL_INFO_1 = "additionalInfo1";
    private static final String KEY_SP_SYNC = "spSync";
    private static final String KEY_UNLIMITED = "unlimited";
    private boolean mAdditionalInfo1;
    private boolean mSpSync;
    private boolean mUnlimited;

    GetCardList() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    GetCardList(JSONObject jsonObject) throws JSONException {
        LogMgr.log(5, "000 %s", jsonObject);
        this.mUnlimited = jsonObject.getBoolean(KEY_UNLIMITED);
        this.mAdditionalInfo1 = jsonObject.getBoolean(KEY_ADDITIONAL_INFO_1);
        this.mSpSync = jsonObject.getBoolean(KEY_SP_SYNC);
        LogMgr.log(5, "999");
    }

    boolean isUnlimited() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mUnlimited;
    }

    boolean isAdditionalInfo1() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mAdditionalInfo1;
    }

    boolean isSpSync() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mSpSync;
    }
}
