package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.PurseData;

/* JADX INFO: compiled from: WriteWithoutEncryption.java */
/* JADX INFO: loaded from: classes.dex */
class i1 extends e1 {
    public i1(int i) {
        super(i);
    }

    @Override // com.felicanetworks.mfw.a.cmn.e1
    protected Data b(String str) {
        return new PurseData(c(str.substring(0, 8)), c(str.substring(8, 16)), b1.I(str.substring(16, 28)), Integer.parseInt(str.substring(28, 32), 16));
    }

    protected long c(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int length = str.length() / 2; length > 0; length--) {
            stringBuffer.append(str.substring((length - 1) * 2, length * 2));
        }
        return Long.parseLong(stringBuffer.toString(), 16);
    }
}
