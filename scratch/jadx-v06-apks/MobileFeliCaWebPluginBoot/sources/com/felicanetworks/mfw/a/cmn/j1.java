package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.PurseDecrementData;

/* JADX INFO: compiled from: WriteWithoutEncryption.java */
/* JADX INFO: loaded from: classes.dex */
class j1 extends i1 {
    public j1(int i) {
        super(i);
    }

    @Override // com.felicanetworks.mfw.a.cmn.i1, com.felicanetworks.mfw.a.cmn.e1
    protected Data b(String str) {
        return new PurseDecrementData(c(str.substring(0, 8)), Integer.parseInt(str.substring(28, 32), 16));
    }
}
