package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes.dex */
public class LocalPartialCardInfo {
    private String mCid;
    private String mIdm;
    private int mPosition;
    private String mServiceId;

    public LocalPartialCardInfo(String str, String str2, int i, String str3) {
        this.mCid = str;
        this.mIdm = str2;
        this.mPosition = i;
        this.mServiceId = str3;
    }

    public String getCid() {
        return this.mCid;
    }

    public String getIDm() {
        return this.mIdm;
    }

    public int getCardPosition() {
        return this.mPosition;
    }

    public String getServiceId() {
        return this.mServiceId;
    }
}
