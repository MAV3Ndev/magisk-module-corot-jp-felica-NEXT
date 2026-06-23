package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public class LocalPartialCardInfo {
    private String mCid;
    private String mIdm;
    private int mPosition;
    private String mServiceId;

    public LocalPartialCardInfo(String cid, String idm, int position, String serviceId) {
        this.mCid = cid;
        this.mIdm = idm;
        this.mPosition = position;
        this.mServiceId = serviceId;
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
