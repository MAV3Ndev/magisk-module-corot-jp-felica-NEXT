package com.felicanetworks.mfc.felica.access_control;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface AccessControlManager {
    public static final String ERROR_MSG_HTTP_COMMUNICATION_ERROR = "HTTP communication error.";
    public static final String ERROR_MSG_INTERRUPTED = "Interrupted.";
    public static final int TYPE_HTTP_ERROR = 3;
    public static final int TYPE_INTERRUPTED_ERROR = 2;
    public static final int TYPE_INVALID_APP_ERROR = 5;
    public static final int TYPE_NOT_FOUND_ERROR = 4;
    public static final int TYPE_UNKNOWN_ERROR = 1;

    AccessController getAccessController();

    String getErrorMessage();

    int getErrorType();

    void init(Context context);

    boolean startAccessControl(String[] strArr, int i, int i2);

    void stopAccessControl();
}
