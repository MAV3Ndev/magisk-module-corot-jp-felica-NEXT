package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class FeliCaResponseThruRwMessage extends Message {
    FeliCaResponseThruRwMessage(int i, byte[] bArr) {
        init((byte) 1, i, (byte) 7, bArr.length);
        System.arraycopy(bArr, 0, this.mData, 6, this.mLength);
    }
}
