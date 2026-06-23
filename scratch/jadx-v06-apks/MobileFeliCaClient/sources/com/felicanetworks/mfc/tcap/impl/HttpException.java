package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class HttpException extends Exception {
    private static final long serialVersionUID = -1387275902799674331L;
    private boolean mIsCanceled;

    HttpException() {
    }

    HttpException(String str) {
        super(str);
    }

    HttpException(String str, boolean z) {
        super(str);
        this.mIsCanceled = z;
    }

    boolean isCanceled() {
        return this.mIsCanceled;
    }
}
