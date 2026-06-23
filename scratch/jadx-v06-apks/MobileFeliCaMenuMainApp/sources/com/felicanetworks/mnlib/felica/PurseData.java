package com.felicanetworks.mnlib.felica;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class PurseData extends Data {
    private static final String EXC_DATA = "The value must be from 0 to 0xffffffff.";
    private static final String EXC_EXEC_ID = "The value must be from 0 to 0xffff.";
    private static final String EXC_LENGTH = "The length must be 6.";
    private static final String EXC_NULL = "null is not allowed.";
    private static final long MAX_DATA = 4294967295L;
    private static final int MAX_EXEC_ID = 65535;
    private static final long MIN_DATA = 0;
    private static final int MIN_EXEC_ID = 0;
    private static final String TAG = "NFC";
    public static final int TYPE = 3;
    private static final int USER_DATA_LENGTH = 6;
    private long mCashBackData;
    private int mExecId;
    private long mPurseData;
    private byte[] mUserData;

    @Override // com.felicanetworks.mnlib.felica.Data
    public int getType() {
        return 3;
    }

    public PurseData(long j, long j2, byte[] bArr, int i) throws IllegalArgumentException {
        setPurseData(j);
        setCashBackData(j2);
        setUserData(bArr);
        setExecID(i);
    }

    public long getPurseData() {
        return this.mPurseData;
    }

    public void setPurseData(long j) throws IllegalArgumentException {
        if (j < 0 || j > MAX_DATA) {
            throw new IllegalArgumentException(EXC_DATA);
        }
        this.mPurseData = j;
    }

    public long getCashBackData() {
        return this.mCashBackData;
    }

    public void setCashBackData(long j) throws IllegalArgumentException {
        if (j < 0 || j > MAX_DATA) {
            throw new IllegalArgumentException(EXC_DATA);
        }
        this.mCashBackData = j;
    }

    public byte[] getUserData() {
        return this.mUserData;
    }

    public void setUserData(byte[] bArr) throws IllegalArgumentException {
        if (bArr == null) {
            throw new IllegalArgumentException(EXC_NULL);
        }
        if (bArr.length != 6) {
            throw new IllegalArgumentException(EXC_LENGTH);
        }
        this.mUserData = bArr;
    }

    public int getExecID() {
        return this.mExecId;
    }

    public void setExecID(int i) throws IllegalArgumentException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
        this.mExecId = i;
    }

    @Override // com.felicanetworks.mnlib.felica.Data
    public void checkFormat() throws IllegalArgumentException {
        long j = this.mPurseData;
        if (j < 0 || j > MAX_DATA) {
            Log.w(TAG, "checkFormat  : purseData = " + this.mPurseData);
            throw new IllegalArgumentException(EXC_DATA);
        }
        long j2 = this.mCashBackData;
        if (j2 < 0 || j2 > MAX_DATA) {
            Log.w(TAG, "checkFormat  : cashBackData = " + this.mCashBackData);
            throw new IllegalArgumentException(EXC_DATA);
        }
        byte[] bArr = this.mUserData;
        if (bArr == null) {
            Log.w(TAG, "checkFormat  : userData is null");
            throw new IllegalArgumentException(EXC_NULL);
        }
        if (bArr.length != 6) {
            Log.w(TAG, "checkFormat  : userData length = " + this.mUserData.length);
            throw new IllegalArgumentException(EXC_LENGTH);
        }
        int i = this.mExecId;
        if (i < 0 || i > 65535) {
            Log.w(TAG, "checkFormat  : execId = " + this.mExecId);
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
    }
}
