package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.RandomData;

/* JADX INFO: compiled from: WriteWithoutEncryption.java */
/* JADX INFO: loaded from: classes.dex */
class k1 extends e1 {
    public k1(int i) {
        super(i);
    }

    @Override // com.felicanetworks.mfw.a.cmn.e1
    protected Data b(String str) {
        return new RandomData(b1.I(str));
    }
}
