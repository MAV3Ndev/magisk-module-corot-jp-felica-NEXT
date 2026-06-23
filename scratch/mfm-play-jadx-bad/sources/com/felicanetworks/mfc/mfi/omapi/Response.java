package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes3.dex */
public class Response {
    private byte[] mResponse;

    protected Response get() {
        return this;
    }

    protected byte[] getResponse() {
        return this.mResponse;
    }

    protected void setResponse(byte[] response) {
        if (response == null) {
            throw new IllegalArgumentException();
        }
        this.mResponse = response;
    }
}
