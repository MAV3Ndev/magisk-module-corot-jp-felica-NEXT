package com.felicanetworks.mfc.mfi.http;

/* JADX INFO: loaded from: classes.dex */
public class ProtocolException extends Exception {
    private static final long serialVersionUID = -3466502893068622491L;
    private boolean mIsCanceled;

    public ProtocolException() {
    }

    public ProtocolException(String str) {
        super(str);
    }

    public ProtocolException(String str, boolean z) {
        super(str);
        this.mIsCanceled = z;
    }

    boolean isCanceled() {
        return this.mIsCanceled;
    }
}
