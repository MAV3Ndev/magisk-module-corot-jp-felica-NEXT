package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockData;
import com.felicanetworks.mfc.CyclicData;
import com.felicanetworks.mfc.Data;

/* JADX INFO: compiled from: WriteWithoutEncryption.java */
/* JADX INFO: loaded from: classes.dex */
class g1 extends e1 {
    public g1(int i) {
        super(i);
    }

    @Override // com.felicanetworks.mfw.a.cmn.e1, com.felicanetworks.mfw.a.cmn.f1
    public BlockData a(int i, String str) {
        return new BlockData(new Block(this.f152a, 0), b(str));
    }

    @Override // com.felicanetworks.mfw.a.cmn.e1
    protected Data b(String str) {
        return new CyclicData(b1.I(str));
    }
}
