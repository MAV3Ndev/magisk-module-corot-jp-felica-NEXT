package com.felicanetworks.mfc.mfi.http;

/* JADX INFO: loaded from: classes3.dex */
public class HttpException extends Exception {
    private static final long serialVersionUID = 8172456309929799393L;
    private boolean mIsCanceled;
    private int mType;

    public HttpException() {
        this.mType = 203;
    }

    public HttpException(String message) {
        super(message);
        this.mType = 203;
    }

    public HttpException(int type, String message) {
        super(message);
        this.mType = type;
    }

    public HttpException(int type, String message, boolean isCanceled) {
        super(message);
        this.mType = type;
        this.mIsCanceled = isCanceled;
    }

    boolean isCanceled() {
        return this.mIsCanceled;
    }

    public int getType() {
        return this.mType;
    }
}
