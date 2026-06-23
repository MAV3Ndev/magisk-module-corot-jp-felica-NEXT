package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockData;
import com.felicanetworks.mfc.Data;

/* JADX INFO: compiled from: WriteWithoutEncryption.java */
/* JADX INFO: loaded from: classes.dex */
abstract class e1 implements f1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected int f152a;

    public e1(int i) {
        this.f152a = i;
    }

    @Override // com.felicanetworks.mfw.a.cmn.f1
    public BlockData a(int i, String str) {
        return new BlockData(new Block(this.f152a, i), b(str));
    }

    protected abstract Data b(String str);
}
