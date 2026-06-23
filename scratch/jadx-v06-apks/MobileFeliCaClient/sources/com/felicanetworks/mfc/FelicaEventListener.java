package com.felicanetworks.mfc;

/* JADX INFO: loaded from: classes.dex */
public interface FelicaEventListener {
    public static final int TYPE_HTTP_ERROR = 3;
    public static final int TYPE_INTERRUPTED_ERROR = 2;
    public static final int TYPE_INVALID_APP_ERROR = 6;
    public static final int TYPE_MFC_VERSION_ERROR = 8;
    public static final int TYPE_NOT_FOUND_ERROR = 4;
    public static final int TYPE_REVOKED_ERROR = 5;
    public static final int TYPE_UNKNOWN_ERROR = 1;
    public static final int TYPE_USED_BY_OTHER_APP = 7;
    public static final int TYPE_UTILITY_VERSION_ERROR = 9;

    void errorOccurred(int i, String str, AppInfo appInfo);

    void finished();
}
