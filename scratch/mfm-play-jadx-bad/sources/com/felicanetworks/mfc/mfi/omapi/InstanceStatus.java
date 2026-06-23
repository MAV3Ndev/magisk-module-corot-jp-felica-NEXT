package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.util.LogMgr;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes3.dex */
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

    void setSystemCode(byte[] systemCode) throws IllegalArgumentException {
        if (systemCode == null || systemCode.length < 2) {
            throw new IllegalArgumentException("Invalid systemCode bytes");
        }
        this.mSystemCode = (systemCode[1] & 255) | ((systemCode[0] & 255) << 8);
    }

    void setIDm(byte[] idm) {
        if (idm != null) {
            this.mIDm = idm;
            return;
        }
        throw new IllegalArgumentException("Invalid IDm bytes");
    }

    void setAid(byte[] aid) {
        if (aid != null) {
            this.mAid = aid;
            return;
        }
        throw new IllegalArgumentException("Invalid AID bytes");
    }

    void setCid(byte[] cid) {
        if (cid != null) {
            this.mCid = cid;
            return;
        }
        throw new IllegalArgumentException("Invalid CID bytes");
    }

    void setLifeCycleActivateState(byte[] lifeCycleActivateState) {
        if (lifeCycleActivateState != null && lifeCycleActivateState.length == 2) {
            this.mLifeCycleActivateState = lifeCycleActivateState;
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
        if (z) {
            return MfiClientConst.NO_CID_INSTANCE_KEY;
        }
        if (z2) {
            return MfiClientConst.ALL_FF_CID_INSTANCE_KEY;
        }
        return new String(this.mCid, Charset.forName("US-ASCII"));
    }
}
