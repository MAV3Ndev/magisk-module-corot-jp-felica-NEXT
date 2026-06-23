package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes.dex */
public class SystemInfo {
    private byte[] mIdm;
    private byte[] mPmm;
    private int mSystemCode;

    SystemInfo(byte[] bArr, byte[] bArr2, int i) {
        this.mIdm = bArr;
        this.mPmm = bArr2;
        this.mSystemCode = i;
    }

    public byte[] getIdm() {
        return this.mIdm;
    }

    public byte[] getPmm() {
        return this.mPmm;
    }

    public int getSystemCode() {
        return this.mSystemCode;
    }
}
