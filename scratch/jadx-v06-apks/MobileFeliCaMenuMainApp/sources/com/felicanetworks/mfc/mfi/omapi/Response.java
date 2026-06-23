package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes.dex */
public class Response {
    private byte[] mResponse;

    protected Response get() {
        return this;
    }

    protected byte[] getResponse() {
        return this.mResponse;
    }

    protected void setResponse(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException();
        }
        this.mResponse = bArr;
    }
}
