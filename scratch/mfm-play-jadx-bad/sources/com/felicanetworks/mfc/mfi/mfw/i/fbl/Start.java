package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
class Start {
    private static final String KEY_ADMIN_START = "adminStart";
    private static final String KEY_SKIP_AGREEMENT_PAGE = "skipAgreementPage";
    private boolean mAdminStart;
    private boolean mSkipAgreementPage;

    Start() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    Start(JSONObject jsonObject) throws JSONException {
        LogMgr.log(5, "000 %s", jsonObject);
        this.mSkipAgreementPage = jsonObject.getBoolean(KEY_SKIP_AGREEMENT_PAGE);
        this.mAdminStart = jsonObject.getBoolean(KEY_ADMIN_START);
        LogMgr.log(5, "999");
    }

    boolean isSkipAgreementPage() {
        return this.mSkipAgreementPage;
    }

    boolean isAdminStart() {
        return this.mAdminStart;
    }
}
