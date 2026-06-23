package com.felicanetworks.mfc.tcap;

/* JADX INFO: loaded from: classes.dex */
public interface FeliCaClientEventListener {
    public static final int TYPE_CANCELED = 1;
    public static final int TYPE_HTTP_ERROR = 2;
    public static final int TYPE_PROTOCOL_ERROR = 3;
    public static final int TYPE_TCAP_IMMEDIATE_STOP = 100;
    public static final int TYPE_UNKNOWN_ERROR = 0;

    void errorOccurred(int i, String str);

    void finished(int i);
}
