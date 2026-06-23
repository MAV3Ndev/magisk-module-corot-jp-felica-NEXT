package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Permit extends JSONObject {
    private static final String KEY_MFC_PERMIT = "mfcPermit";
    private static final String KEY_MFI_PERMIT = "mfiPermit";
    private static final String KEY_PERMIT_ID = "permitId";
    private static final String KEY_PLATFORM_TYPE = "platformType";
    private static final String KEY_SEP_ID = "sepId";
    private static final String KEY_USAGE_COUNT_UPPER_LIMIT = "usageCountUpperLimit";
    private static final String KEY_WALLET_APP_CALLER_INFO = "walletAppCallerInfo";
    private static final String KEY_WALLET_APP_ID = "walletAppId";
    private static final String KEY_WALLET_APP_IDENTIFIABLE_INFO = "walletAppIdentifiableInfo";
    private static final int LEN_SEP_ID = 6;
    private MfcPermit mMfcPermit;
    private MfiPermit mMfiPermit;
    private String mPermitId;
    private String mPlatformType;
    private String mSepId;
    private int mUsageCountUpperLimit;
    private String mWalletAppCallerInfo;
    private String mWalletAppId;
    private String mWalletAppIdentifiableInfo;

    public Permit() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    public Permit(String str) throws JSONException {
        super(str);
        LogMgr.log(5, "000 %s", str);
        this.mPermitId = JsonUtil.checkString((JSONObject) this, KEY_PERMIT_ID, false, 0);
        this.mPlatformType = JsonUtil.checkString((JSONObject) this, KEY_PLATFORM_TYPE, false, 0);
        this.mWalletAppId = JsonUtil.checkString((JSONObject) this, KEY_WALLET_APP_ID, true, 0);
        this.mWalletAppCallerInfo = JsonUtil.checkString((JSONObject) this, KEY_WALLET_APP_CALLER_INFO, true, 0);
        this.mWalletAppIdentifiableInfo = JsonUtil.checkString((JSONObject) this, KEY_WALLET_APP_IDENTIFIABLE_INFO, true, 0);
        this.mSepId = JsonUtil.checkString((JSONObject) this, KEY_SEP_ID, true, 6);
        this.mUsageCountUpperLimit = getInt(KEY_USAGE_COUNT_UPPER_LIMIT);
        this.mMfcPermit = new MfcPermit(JsonUtil.checkObject((JSONObject) this, KEY_MFC_PERMIT, true));
        this.mMfiPermit = new MfiPermit(JsonUtil.checkObject((JSONObject) this, KEY_MFI_PERMIT, true));
        LogMgr.log(5, "999");
    }

    public String getPermitId() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mPermitId;
    }

    public String getPlatformType() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mPlatformType;
    }

    public String getWalletAppId() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mWalletAppId;
    }

    public String getWalletAppCallerInfo() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mWalletAppCallerInfo;
    }

    public String getWalletAppIdentifiableInfo() {
        return this.mWalletAppIdentifiableInfo;
    }

    public String getSepId() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mSepId;
    }

    public int getUsageCountUpperLimit() {
        return this.mUsageCountUpperLimit;
    }

    MfcPermit getMfcPermit() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mMfcPermit;
    }

    MfiPermit getMfiPermit() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mMfiPermit;
    }
}
