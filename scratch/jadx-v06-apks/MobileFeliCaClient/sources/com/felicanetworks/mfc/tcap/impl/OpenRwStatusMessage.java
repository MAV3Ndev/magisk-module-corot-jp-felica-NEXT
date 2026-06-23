package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class OpenRwStatusMessage extends Message {
    public static final byte RESULT_FAILURE = 0;
    public static final byte RESULT_SUCCESS = 1;

    public OpenRwStatusMessage(int i, byte b) {
        init((byte) 1, i, (byte) 2, 1);
        this.mData[6] = b;
    }
}
