package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;

/* JADX INFO: loaded from: classes3.dex */
public class Response {
    private byte[] mResponse;

    protected Response get() {
        return this;
    }

    protected byte[] getResponse() {
        return this.mResponse;
    }

    protected void setResponse(byte[] bArr) {
        LogMgr.log(6, "000");
        if (bArr == null) {
            LogMgr.log(6, "998");
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
        this.mResponse = bArr;
    }
}
