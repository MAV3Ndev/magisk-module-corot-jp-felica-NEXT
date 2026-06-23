package com.felicanetworks.tis.datatype;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TransactionInfoGroup {
    private byte[] mCid;
    private byte[] mIdm;
    private List<TransactionInfo> mInfoList;
    private int mSystemCode;

    public TransactionInfoGroup(int i, byte[] bArr, byte[] bArr2, List<TransactionInfo> list) {
        this.mSystemCode = i;
        this.mIdm = bArr;
        this.mCid = bArr2;
        this.mInfoList = list;
    }

    public int getSystemCode() {
        return this.mSystemCode;
    }

    public byte[] getIdm() {
        return this.mIdm;
    }

    public byte[] getCid() {
        return this.mCid;
    }

    public List<TransactionInfo> getTransactionInfoList() {
        return this.mInfoList;
    }
}
