package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes.dex */
public class MigrateCardInstanceInfo {
    private byte[] mAid;
    private boolean mHasCid;

    public void setAid(byte[] bArr) {
        if (bArr != null) {
            this.mAid = bArr;
            return;
        }
        throw new IllegalArgumentException("Invalid AID bytes");
    }

    public byte[] getAid() {
        return this.mAid;
    }

    public void setHasCid(boolean z) {
        this.mHasCid = z;
    }

    public boolean hasCid() {
        return this.mHasCid;
    }
}
