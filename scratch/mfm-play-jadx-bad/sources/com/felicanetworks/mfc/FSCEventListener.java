package com.felicanetworks.mfc;

/* JADX INFO: loaded from: classes3.dex */
public interface FSCEventListener {
    public static final int TYPE_HTTP_ERROR = 3;
    public static final int TYPE_INTERRUPTED_ERROR = 2;
    public static final int TYPE_PROTOCOL_ERROR = 4;
    public static final int TYPE_TCAP_IMMEDIATE_STOP = 100;
    public static final int TYPE_UNKNOWN_ERROR = 1;

    void errorOccurred(int type, String message);

    void finished(int status);

    byte[] operationRequested(int deviceID, String param, byte[] data) throws Exception;
}
