package com.felicanetworks.mfsctrl.chip;

/* JADX INFO: loaded from: classes.dex */
public interface FelicaInitializeListener {

    @Deprecated
    public static final int RESULT_CODE_ERROR_UNKNOWN = 3;
    public static final int RESULT_CODE_FELICA_LOCK_ERROR = 4;
    public static final int RESULT_CODE_NOT_APPLICABLE_DEVICE = 6;
    public static final int RESULT_CODE_WARNING_COMMUNICATIONERROR = 0;
    public static final int RESULT_CODE_WARNING_INITFAILED = 1;
    public static final int RESULT_CODE_WARNING_OVERCROWDING = 5;

    void completeActivate();

    void completeLinkageData();

    void onComplete();

    void onError(String str);

    void onWarning(int i);
}
