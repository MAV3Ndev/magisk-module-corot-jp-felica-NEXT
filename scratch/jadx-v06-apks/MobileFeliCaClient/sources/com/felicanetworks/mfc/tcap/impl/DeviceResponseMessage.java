package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.FelicaConst;

/* JADX INFO: loaded from: classes.dex */
public class DeviceResponseMessage extends Message {
    public DeviceResponseMessage(int i, byte[] bArr) {
        init((byte) 0, i, (byte) 38, bArr != null ? 4 + bArr.length : 4);
        this.mData[6] = 0;
        this.mData[7] = 0;
        if (bArr != null) {
            this.mData[8] = (byte) ((bArr.length & FelicaConst.WILD_CARD_SYSTEM_CODE3) >> 8);
            this.mData[9] = (byte) (bArr.length & 255);
            System.arraycopy(bArr, 0, this.mData, 10, bArr.length);
        } else {
            this.mData[8] = 0;
            this.mData[9] = 0;
        }
    }
}
