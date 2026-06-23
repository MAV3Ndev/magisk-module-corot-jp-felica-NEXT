package com.felicanetworks.mfc;

/* JADX INFO: loaded from: classes.dex */
public interface FSCEventListener {
    public static final int TYPE_HTTP_ERROR = 3;
    public static final int TYPE_INTERRUPTED_ERROR = 2;
    public static final int TYPE_PROTOCOL_ERROR = 4;
    public static final int TYPE_TCAP_IMMEDIATE_STOP = 100;
    public static final int TYPE_UNKNOWN_ERROR = 1;

    void errorOccurred(int i, String str);

    void finished(int i);

    byte[] operationRequested(int i, String str, byte[] bArr) throws Exception;
}
