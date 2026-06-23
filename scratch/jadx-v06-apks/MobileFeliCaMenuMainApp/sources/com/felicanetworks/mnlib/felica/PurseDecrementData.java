package com.felicanetworks.mnlib.felica;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class PurseDecrementData extends Data {
    private static final String EXC_DATA = "The value must be from 0 to 0xffffffff.";
    private static final String EXC_EXEC_ID = "The value must be from 0 to 0xffff.";
    private static final long MAX_DATA = 4294967295L;
    private static final int MAX_EXEC_ID = 65535;
    private static final long MIN_DATA = 0;
    private static final int MIN_EXEC_ID = 0;
    private static final String TAG = "NFC";
    public static final int TYPE = 5;
    private long mDecrementData;
    private int mExecId;

    @Override // com.felicanetworks.mnlib.felica.Data
    public int getType() {
        return 5;
    }

    public PurseDecrementData(long j, int i) throws IllegalArgumentException {
        setDecrementData(j);
        setExecID(i);
    }

    public long getDecrementData() {
        return this.mDecrementData;
    }

    public void setDecrementData(long j) throws IllegalArgumentException {
        if (j < 0 || j > MAX_DATA) {
            throw new IllegalArgumentException(EXC_DATA);
        }
        this.mDecrementData = j;
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
        long j = this.mDecrementData;
        if (j < 0 || j > MAX_DATA) {
            Log.w(TAG, "checkFormat  : decrementData = " + this.mDecrementData);
            throw new IllegalArgumentException(EXC_DATA);
        }
        int i = this.mExecId;
        if (i < 0 || i > 65535) {
            Log.w(TAG, "checkFormat  : execId = " + this.mExecId);
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
    }
}
