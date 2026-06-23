package com.felicanetworks.mfsctrl;

import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;

/* JADX INFO: loaded from: classes.dex */
public interface CheckInitializationListener {
    public static final int FELICA_HTTP_ERROR = 10;
    public static final int RESULT_CODE_COMPLETE_DONEINITIALIZATION = 1;
    public static final int RESULT_CODE_COMPLETE_NEEDINITIALIZATION = 0;
    public static final int RESULT_CODE_ERROR_FELICATIMEOUT = 7;
    public static final int RESULT_CODE_ERROR_MFCOTHER = 8;
    public static final int RESULT_CODE_ERROR_MFCPERMINSPECT = 6;
    public static final int RESULT_CODE_ERROR_UNKNOWN = 5;
    public static final int RESULT_CODE_MFC_DISABLE = 12;
    public static final int RESULT_CODE_MFC_NOT_FOUND = 11;
    public static final int RESULT_CODE_MFIC_NOT_FOUND = 9;
    public static final int RESULT_CODE_MFS_VERSION_UP = 13;
    public static final int RESULT_CODE_WARNING_COMMUNICATIONERROR = 14;
    public static final int RESULT_CODE_WARNING_FELICACHIPINUSER = 2;
    public static final int RESULT_CODE_WARNING_LOCKEDFELICACHIP = 3;

    @Deprecated
    public static final int RESULT_DATA_NONE = 0;

    void onComplete(int i);

    void onError(int i, String str, FelicaErrorInfo felicaErrorInfo);

    void onWarning(int i, int i2);
}
