package com.felicanetworks.mfc.tcap.impl.v25;

import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaParamPool {
    private Hashtable<Integer, byte[]> mPool;

    public void saveParam(int i, byte[] bArr, int i2, int i3) {
        Integer num = new Integer(i);
        byte[] bArr2 = new byte[i3];
        if (this.mPool == null) {
            this.mPool = new Hashtable<>();
        }
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        this.mPool.put(num, bArr2);
    }

    public byte[] callParam(int i) {
        Integer num = new Integer(i);
        Hashtable<Integer, byte[]> hashtable = this.mPool;
        if (hashtable == null) {
            return null;
        }
        return hashtable.get(num);
    }

    public void clearParams() {
        Hashtable<Integer, byte[]> hashtable = this.mPool;
        if (hashtable == null) {
            return;
        }
        hashtable.clear();
    }
}
