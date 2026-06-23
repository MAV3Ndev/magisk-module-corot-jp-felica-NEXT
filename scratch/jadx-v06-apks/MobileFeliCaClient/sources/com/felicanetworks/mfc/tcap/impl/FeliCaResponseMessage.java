package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaResponseMessage extends Message {
    public FeliCaResponseMessage(int i, byte[] bArr) {
        init((byte) 1, i, (byte) 2, bArr.length);
        System.arraycopy(bArr, 0, this.mData, 6, this.mLength);
    }
}
