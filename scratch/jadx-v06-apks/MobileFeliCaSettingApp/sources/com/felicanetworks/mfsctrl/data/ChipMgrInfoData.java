package com.felicanetworks.mfsctrl.data;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class ChipMgrInfoData {
    public byte[] container;
    public String icCode;
    public byte[] idm;

    public ChipMgrInfoData(byte[] bArr, String str, byte[] bArr2) {
        this.idm = null;
        this.icCode = null;
        this.container = null;
        this.idm = bArr;
        this.icCode = str;
        this.container = bArr2;
    }

    public String toString() {
        return "ChipMgrInfoData[idm:" + Arrays.toString(this.idm) + " icCode:" + this.icCode + " container:" + Arrays.toString(this.container) + "]";
    }
}
