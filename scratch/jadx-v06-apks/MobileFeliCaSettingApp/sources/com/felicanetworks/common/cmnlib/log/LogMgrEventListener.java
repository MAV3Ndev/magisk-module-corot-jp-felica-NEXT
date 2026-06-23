package com.felicanetworks.common.cmnlib.log;

/* JADX INFO: loaded from: classes.dex */
public interface LogMgrEventListener {
    public static final int FAILED_CONNECTION = -2;
    public static final int FAILED_OTHER = -3;
    public static final int FAILED_TIMEOUT = -1;
    public static final int SUCCEEDED = 0;

    void finished(int i, String str);
}
