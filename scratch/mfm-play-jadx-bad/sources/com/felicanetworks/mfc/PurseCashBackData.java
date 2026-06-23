package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PurseCashBackData extends Data {
    public static final Parcelable.Creator<PurseCashBackData> CREATOR = new Parcelable.Creator<PurseCashBackData>() { // from class: com.felicanetworks.mfc.PurseCashBackData.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseCashBackData createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new PurseCashBackData(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseCashBackData[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new PurseCashBackData[size];
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

    public PurseCashBackData(long cashBackData, int execID) throws IllegalArgumentException {
        setCashBackData(cashBackData);
        setExecID(execID);
    }

    public long getCashBackData() {
        return this.cashBackData;
    }

    public void setCashBackData(long cashBackData) throws IllegalArgumentException {
        if (cashBackData < 0 || cashBackData > MAX_DATA) {
            throw new IllegalArgumentException(EXC_DATA);
        }
        this.cashBackData = cashBackData;
    }

    public int getExecID() {
        return this.execID;
    }

    public void setExecID(int execID) throws IllegalArgumentException {
        if (execID < 0 || execID > 65535) {
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
        this.execID = execID;
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.cashBackData = in.readLong();
        this.execID = in.readInt();
        LogMgr.log(6, "001 cashBackData=%d execID=%d", Long.valueOf(this.cashBackData), Integer.valueOf(this.execID));
        LogMgr.log(6, "999");
    }

    private PurseCashBackData(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        out.writeLong(this.cashBackData);
        out.writeInt(this.execID);
        LogMgr.log(6, "001 cashBackData=%d execID=%d", Long.valueOf(this.cashBackData), Integer.valueOf(this.execID));
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        long j = this.cashBackData;
        if (j < 0 || j > MAX_DATA) {
            LogMgr.log(1, "%s : cashBackData = %d", "800", Long.valueOf(j));
            throw new IllegalArgumentException(EXC_DATA);
        }
        int i = this.execID;
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s : execID = %d", "801", Integer.valueOf(i));
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
        LogMgr.log(4, "999");
    }
}
