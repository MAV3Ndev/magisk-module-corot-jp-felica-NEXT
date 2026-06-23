package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class FunctionCategory {
    private static final String KEY_ACCESS = "access";
    private static final String KEY_CLEAR_ACCOUNT = "clearAccount";
    private static final String KEY_ENABLE_DISABLE = "enableDisable";
    private static final String KEY_EXIST_EMPTY_SLOT = "existEmptySlot";
    private static final String KEY_GET_INFO = "getInfo";
    private static final String KEY_IDENTIFY_SERVICE = "identifyService";
    private static final String KEY_INITIALIZE = "initialize";
    private static final String KEY_ISSUE_DELETE = "issueDelete";
    private static final String KEY_PRIVILEGE = "limitedAdmin";
    private static final String KEY_USE_UNSUPPORT_MFI_SERVICE1_CARD = "useUnsupportMfiService1Card";
    private boolean mAccess;
    private boolean mClearAccount;
    private boolean mEnableDisable;
    private boolean mExistEmptySlot;
    private boolean mGetInfo;
    private boolean mIdentifyService;
    private boolean mInitialize;
    private boolean mIssueDelete;
    private boolean mPrivilege;
    private boolean mUseUnsupportMfiService1Card;

    FunctionCategory() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    FunctionCategory(JSONObject jSONObject, boolean z) throws JSONException {
        LogMgr.log(5, "000 %s", jSONObject);
        this.mGetInfo = jSONObject.getBoolean(KEY_GET_INFO);
        this.mIssueDelete = jSONObject.getBoolean(KEY_ISSUE_DELETE);
        this.mEnableDisable = jSONObject.getBoolean(KEY_ENABLE_DISABLE);
        this.mAccess = jSONObject.getBoolean(KEY_ACCESS);
        this.mIdentifyService = jSONObject.getBoolean(KEY_IDENTIFY_SERVICE);
        this.mClearAccount = jSONObject.getBoolean(KEY_CLEAR_ACCOUNT);
        this.mInitialize = jSONObject.getBoolean(KEY_INITIALIZE);
        this.mUseUnsupportMfiService1Card = JsonUtil.checkBoolean(jSONObject, KEY_USE_UNSUPPORT_MFI_SERVICE1_CARD, false);
        if (z) {
            this.mPrivilege = !JsonUtil.checkBoolean(jSONObject, KEY_PRIVILEGE, false);
            if (!jSONObject.has(KEY_PRIVILEGE) || jSONObject.isNull(KEY_PRIVILEGE)) {
                this.mPrivilege = true;
            }
        } else {
            this.mPrivilege = false;
        }
        LogMgr.log(6, "001 adminStart: " + z);
        LogMgr.log(6, "002 privilege: " + this.mPrivilege);
        this.mExistEmptySlot = JsonUtil.checkBoolean(jSONObject, KEY_EXIST_EMPTY_SLOT, false);
        LogMgr.log(5, "999");
    }

    boolean isGetInfo() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mGetInfo;
    }

    boolean isIssueDelete() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mIssueDelete;
    }

    boolean isEnableDisable() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mEnableDisable;
    }

    boolean isAccess() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mAccess;
    }

    boolean isIdentifyService() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mIdentifyService;
    }

    boolean isClearAccount() {
        return this.mClearAccount;
    }

    boolean isInitialize() {
        return this.mInitialize;
    }

    boolean isUseUnsupportMfiService1Card() {
        return this.mUseUnsupportMfiService1Card;
    }

    boolean isPrivileged() {
        return this.mPrivilege;
    }

    boolean isExistEmptySlot() {
        return this.mExistEmptySlot;
    }
}
