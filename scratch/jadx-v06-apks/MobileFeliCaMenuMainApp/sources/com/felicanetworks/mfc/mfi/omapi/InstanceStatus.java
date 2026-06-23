package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.util.LogMgr;
import java.nio.charset.Charset;

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
        return (bArr == null || bArr.length != 2 || (bArr[1] & ACTIVATE_MASK) == 0) ? false : true;
    }

    public String getCidString() {
        LogMgr.log(6, "000");
        LogMgr.logArray(6, this.mCid);
        byte[] bArr = this.mCid;
        if (bArr == null || bArr.length != 63) {
            return null;
        }
        boolean z = true;
        boolean z2 = true;
        for (byte b : bArr) {
            if (b != 0) {
                z = false;
            }
            if (b != -1) {
                z2 = false;
            }
        }
        return z ? MfiClientConst.NO_CID_INSTANCE_KEY : z2 ? MfiClientConst.ALL_FF_CID_INSTANCE_KEY : new String(this.mCid, Charset.forName("US-ASCII"));
    }
}
