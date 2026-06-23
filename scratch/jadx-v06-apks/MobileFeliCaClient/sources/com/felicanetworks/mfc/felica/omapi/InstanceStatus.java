package com.felicanetworks.mfc.felica.omapi;

/* JADX INFO: loaded from: classes.dex */
public class InstanceStatus {
    private static final byte ACTIVATE_MASK = 1;
    private static final int ACTIVATION_STATE = 1;
    private static final int LIFE_CYCLE_ACTIVATION_STATE_LEN = 2;
    private static final int LIFE_CYCLE_STATE = 0;
    private static final byte PERSONALIZED = 15;
    private int mSystemCode = 65536;
    private byte[] mIDm = null;
    private byte[] mAid = null;
    private byte[] mCid = null;
    private byte[] mLifeCycleActivateState = null;

    void setSystemCode(byte[] bArr) throws IllegalArgumentException {
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException("Invalid systemCode bytes");
        }
        this.mSystemCode = (bArr[1] & 255) | ((bArr[0] & 255) << 8);
    }

    void setIDm(byte[] bArr) {
        if (bArr != null) {
            this.mIDm = bArr;
            return;
        }
        throw new IllegalArgumentException("Invalid IDm bytes");
    }

    void setAid(byte[] bArr) {
        if (bArr != null) {
            this.mAid = bArr;
            return;
        }
        throw new IllegalArgumentException("Invalid AID bytes");
    }

    void setCid(byte[] bArr) {
        if (bArr != null) {
            this.mCid = bArr;
            return;
        }
        throw new IllegalArgumentException("Invalid CID bytes");
    }

    void setLifeCycleActivateState(byte[] bArr) {
        if (bArr != null && bArr.length == 2) {
            this.mLifeCycleActivateState = bArr;
            return;
        }
        throw new IllegalArgumentException("Invalid lifeCycleActivateState bytes");
    }

    public int getSystemCode() {
        return this.mSystemCode;
    }

    public byte[] getIDm() {
        return this.mIDm;
    }

    public byte[] getAid() {
        return this.mAid;
    }

    public byte[] getCid() {
        return this.mCid;
    }

    public boolean isPersonalized() throws IllegalArgumentException {
        byte[] bArr = this.mLifeCycleActivateState;
        return bArr != null && bArr.length == 2 && bArr[0] == 15;
    }

    public boolean isActivated() throws IllegalArgumentException {
        byte[] bArr = this.mLifeCycleActivateState;
        return (bArr == null || bArr.length != 2 || (bArr[1] & 1) == 0) ? false : true;
    }
}
