package com.felicanetworks.mfsctrl;

import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;

/* JADX INFO: loaded from: classes.dex */
public interface RunInitializationListener {

    @Deprecated
    public static final int RESULT_CODE_COMPLETE = 0;
    public static final int RESULT_CODE_ERROR_FELICATIMEOUT = 12;
    public static final int RESULT_CODE_ERROR_MFCOTHER = 15;
    public static final int RESULT_CODE_ERROR_MFCPERMINSPECT = 11;
    public static final int RESULT_CODE_ERROR_UNKNOWN = 9;
    public static final int RESULT_CODE_MFIC_VERSION_ERROR = 13;

    @Deprecated
    public static final int RESULT_CODE_PROGRESS = 1;
    public static final int RESULT_CODE_WARNING_COMMUNICATIONERROR = 8;
    public static final int RESULT_CODE_WARNING_FAILED = 5;
    public static final int RESULT_CODE_WARNING_FELICACHIPINUSE = 6;
    public static final int RESULT_CODE_WARNING_LOCKEDFELICACHIP = 7;
    public static final int RESULT_CODE_WARNING_NOT_APPLICABLE_DEVICE = 16;
    public static final int RESULT_CODE_WARNING_OVERCROWDING = 3;
    public static final int RESULT_CODE_WARNING_SERVERMAINTENANCE = 4;

    @Deprecated
    public static final int RESULT_DATA_NONE = 0;

    void onComplete();

    void onError(int i, String str, FelicaErrorInfo felicaErrorInfo);

    void onProgress(int i);

    void onWarning(int i, int i2);
}
