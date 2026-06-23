package com.felicanetworks.mfm.oss;

import com.felicanetworks.mfm.oss.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class OssLicensesInfoCache {
    private static OssLicensesInfoCache sInstance;
    private OssLicensesInfo mOssLicensesInfo = null;

    private OssLicensesInfoCache() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    public static synchronized OssLicensesInfoCache getInstance() {
        LogMgr.log(6, "000");
        if (sInstance == null) {
            LogMgr.log(7, "001");
            sInstance = new OssLicensesInfoCache();
        }
        LogMgr.log(6, "999");
        return sInstance;
    }

    public synchronized void updateOssLicenseInfo(OssLicensesInfo ossLicensesInfo) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        this.mOssLicensesInfo = ossLicensesInfo;
    }

    public synchronized OssLicensesInfo getOssLicenseInfo() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mOssLicensesInfo;
    }
}
