package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class PurseCashBackData extends Data {
    public static final Parcelable.Creator<PurseCashBackData> CREATOR = new Parcelable.Creator<PurseCashBackData>() { // from class: com.felicanetworks.mfc.PurseCashBackData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseCashBackData createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new PurseCashBackData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseCashBackData[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new PurseCashBackData[i];
        }
    };
    private static final String EXC_DATA = "The value must be from 0 to 0xffffffff.";
    private static final String EXC_EXEC_ID = "The value must be from 0 to 0xffff.";
    private static final long MAX_DATA = 4294967295L;
    private static final int MAX_EXEC_ID = 65535;
    private static final long MIN_DATA = 0;
    private static final int MIN_EXEC_ID = 0;
    public static final int TYPE = 4;
    private long cashBackData;
    private int execID;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.Data
    public int getType() {
        return 4;
    }

    public PurseCashBackData(long j, int i) throws IllegalArgumentException {
        setCashBackData(j);
        setExecID(i);
    }

    public long getCashBackData() {
        return this.cashBackData;
    }

    public void setCashBackData(long j) throws IllegalArgumentException {
        if (j < MIN_DATA || j > MAX_DATA) {
            throw new IllegalArgumentException(EXC_DATA);
        }
        this.cashBackData = j;
    }

    public int getExecID() {
        return this.execID;
    }

    public void setExecID(int i) throws IllegalArgumentException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
        this.execID = i;
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.cashBackData = parcel.readLong();
        this.execID = parcel.readInt();
        LogMgr.log(6, "001 cashBackData=%d execID=%d", Long.valueOf(this.cashBackData), Integer.valueOf(this.execID));
        LogMgr.log(6, "999");
    }

    private PurseCashBackData(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeLong(this.cashBackData);
        parcel.writeInt(this.execID);
        LogMgr.log(6, "001 cashBackData=%d execID=%d", Long.valueOf(this.cashBackData), Integer.valueOf(this.execID));
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        long j = this.cashBackData;
        if (j < MIN_DATA || j > MAX_DATA) {
            LogMgr.log(1, "%s : cashBackData = %d", "800", Long.valueOf(this.cashBackData));
            throw new IllegalArgumentException(EXC_DATA);
        }
        int i = this.execID;
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s : execID = %d", "801", Integer.valueOf(this.execID));
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
        LogMgr.log(4, "999");
    }
}
