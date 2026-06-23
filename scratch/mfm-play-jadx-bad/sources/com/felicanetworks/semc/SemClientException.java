package com.felicanetworks.semc;

import com.felicanetworks.semc.util.LogMgrUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientException extends Exception {
    public static final int EC_FOREGROUND_SERVICE_START_NOT_ALLOWED_ERROR = 505;
    public static final int EC_ILLEGAL_STATE_USED_BY_OTHER_APP_ERROR = 101;
    public static final int EC_ILLEGAL_STATE_USED_BY_REMOTE_ERROR = 103;
    public static final int EC_INTERNAL_ERROR = 900;
    public static final int EC_INVALID_LINKAGE_DATA_ERROR = 102;
    public static final int EC_INVALID_MODE_ERROR = 104;
    public static final int EC_INVALID_PROFILE_ERROR = 504;
    public static final int EC_IPC_ERROR = 901;
    public static final int EC_NOT_SUPPORTED_DEVICE_ERROR = 201;
    public static final int EC_NOT_SUPPORTED_DEVICE_USER_ERROR = 503;
    public static final int EC_NOT_SUPPORTED_OS_ERROR = 500;
    public static final int EC_PERMISSION_ERROR = 100;
    public static final int EC_SEMC_APP_NOT_FOUND_ERROR = 506;
    public static final int EC_SEM_CLIENT_NOT_FOUND_ERROR = 501;
    public static final int EC_SEM_CLIENT_VERSION_ERROR = 502;
    public static final int EC_SERVER_COMMUNICATION_ERROR = 300;
    public static final int EC_SERVER_SYSTEM_ERROR = 401;
    public static final int EC_SERVER_UNAVAILABLE_ERROR = 400;
    public static final int EC_SE_ACCESS_CONFLICTED_ERROR = 202;
    public static final int EC_SE_ACCESS_ERROR = 200;
    public static final int EC_SE_OPERATION_FAILED = 204;
    public static final int EC_SE_UNAVAILABLE_ERROR = 203;
    public static final int EC_SYSTEM_CONDITION_ERROR = 205;
    private String mAdditionalErrorInfo;
    private int mErrorCode;

    public SemClientException() {
        this.mAdditionalErrorInfo = null;
        LogMgrUtil.log(7, "000");
        LogMgrUtil.log(7, "999");
    }

    public SemClientException(int i, String str) {
        super(str);
        this.mAdditionalErrorInfo = null;
        LogMgrUtil.log(7, "000 id=" + i + " message=" + str);
        this.mErrorCode = i;
        this.mAdditionalErrorInfo = "";
        LogMgrUtil.log(7, "999");
    }

    public SemClientException(int i, String str, String str2) {
        super(str2);
        this.mAdditionalErrorInfo = null;
        LogMgrUtil.log(7, "000 id=" + i + " additionalErrorInfo+" + str + " message=" + str2);
        this.mErrorCode = i;
        this.mAdditionalErrorInfo = str;
        LogMgrUtil.log(7, "999");
    }

    public int getErrorCode() {
        LogMgrUtil.log(8, "000");
        LogMgrUtil.log(8, "999 id=" + this.mErrorCode);
        return this.mErrorCode;
    }

    public String getAdditionalErrorInfo() {
        LogMgrUtil.log(8, "000");
        LogMgrUtil.log(8, "999 additionalErrorInfo=" + this.mAdditionalErrorInfo);
        return this.mAdditionalErrorInfo;
    }
}
