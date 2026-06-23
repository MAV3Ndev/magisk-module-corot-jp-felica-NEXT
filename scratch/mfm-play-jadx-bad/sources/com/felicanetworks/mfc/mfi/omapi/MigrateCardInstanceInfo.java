package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes3.dex */
public class MigrateCardInstanceInfo {
    private byte[] mAid;
    private boolean mHasCid;

    public void setAid(byte[] aid) {
        if (aid != null) {
            this.mAid = aid;
            return;
        }
        throw new IllegalArgumentException("Invalid AID bytes");
    }

    public byte[] getAid() {
        return this.mAid;
    }

    public void setHasCid(boolean hasCid) {
        this.mHasCid = hasCid;
    }

    public boolean hasCid() {
        return this.mHasCid;
    }
}
