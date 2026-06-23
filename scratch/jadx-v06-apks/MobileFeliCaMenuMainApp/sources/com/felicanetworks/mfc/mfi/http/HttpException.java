package com.felicanetworks.mfc.mfi.http;

/* JADX INFO: loaded from: classes.dex */
public class HttpException extends Exception {
    private static final long serialVersionUID = 8172456309929799393L;
    private boolean mIsCanceled;
    private int mType;

    public HttpException() {
        this.mType = 203;
    }

    public HttpException(String str) {
        super(str);
        this.mType = 203;
    }

    public HttpException(int i, String str) {
        super(str);
        this.mType = 203;
        this.mType = i;
    }

    public HttpException(int i, String str, boolean z) {
        super(str);
        this.mType = 203;
        this.mType = i;
        this.mIsCanceled = z;
    }

    boolean isCanceled() {
        return this.mIsCanceled;
    }

    public int getType() {
        return this.mType;
    }
}
