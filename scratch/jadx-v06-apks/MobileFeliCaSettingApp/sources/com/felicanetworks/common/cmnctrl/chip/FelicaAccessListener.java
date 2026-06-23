package com.felicanetworks.common.cmnctrl.chip;

import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;

/* JADX INFO: loaded from: classes.dex */
public interface FelicaAccessListener {
    public static final int TYPE_COMMUNICATIONERROR = 7;
    public static final int TYPE_FELICA_HTTP_ERROR = 9;
    public static final int TYPE_FELICA_LOCK_ERROR = 1;
    public static final int TYPE_FELICA_TIMEOUT_ERROR = 4;
    public static final int TYPE_INITFAILED = 10;
    public static final int TYPE_MFC_OTHER_ERROR = 5;
    public static final int TYPE_MFC_PERM_INSPECT_ERROR = 3;
    public static final int TYPE_MFIC_NOT_FOUND = 6;
    public static final int TYPE_OTHER_APP_USE = 0;
    public static final int TYPE_OTHER_ERROR = 2;
    public static final int TYPE_OVERCROWDING = 8;

    void completeActivate();

    void errorResult(int i, String str, int i2, FelicaErrorInfo felicaErrorInfo);

    void finishResult();
}
