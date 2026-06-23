package com.felicanetworks.mfc.mfi.http;

/* JADX INFO: loaded from: classes3.dex */
public class ProtocolException extends Exception {
    private static final long serialVersionUID = -3466502893068622491L;
    private boolean mIsCanceled;

    public ProtocolException() {
    }

    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(String message, boolean isCanceled) {
        super(message);
        this.mIsCanceled = isCanceled;
    }

    boolean isCanceled() {
        return this.mIsCanceled;
    }
}
