package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaInstanceInfo {
    private final byte[] mAid;
    private final byte[] mCid;
    private final byte[] mIdm;
    private final boolean mIsActivated;
    private final int mSystemCode;

    public FelicaInstanceInfo(InstanceStatus instanceStatus) {
        this.mAid = instanceStatus.getAid();
        this.mCid = instanceStatus.getCid();
        this.mIdm = instanceStatus.getIDm();
        this.mSystemCode = instanceStatus.getSystemCode();
        this.mIsActivated = instanceStatus.isActivated();
    }

    public FelicaInstanceInfo(SelectResponse selectResponse) {
        this.mAid = selectResponse.getAid();
        this.mCid = selectResponse.getCid();
        this.mIdm = selectResponse.getIdm();
        this.mSystemCode = selectResponse.getSystemCode();
        this.mIsActivated = selectResponse.isActivated();
    }

    public byte[] getAid() {
        return this.mAid;
    }

    public byte[] getCid() {
        return this.mCid;
    }

    public byte[] getIdm() {
        return this.mIdm;
    }

    public int getSystemCode() {
        return this.mSystemCode;
    }

    public boolean isActivated() {
        return this.mIsActivated;
    }
}
