package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PurseDecrementData extends Data {
    public static final Parcelable.Creator<PurseDecrementData> CREATOR = new Parcelable.Creator<PurseDecrementData>() { // from class: com.felicanetworks.mfc.PurseDecrementData.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseDecrementData createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new PurseDecrementData(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseDecrementData[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new PurseDecrementData[size];
        }
    };
    private static final String EXC_DATA = "The value must be from 0 to 0xffffffff.";
    private static final String EXC_EXEC_ID = "The value must be from 0 to 0xffff.";
    private static final long MAX_DATA = 4294967295L;
    private static final int MAX_EXEC_ID = 65535;
    private static final long MIN_DATA = 0;
    private static final int MIN_EXEC_ID = 0;
    public static final int TYPE = 5;
    private long decrementData;
    private int execID;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.Data
    public int getType() {
        return 5;
    }

    public PurseDecrementData(long decrementData, int execID) throws IllegalArgumentException {
        setDecrementData(decrementData);
        setExecID(execID);
    }

    public long getDecrementData() {
        return this.decrementData;
    }

    public void setDecrementData(long decrementData) throws IllegalArgumentException {
        if (decrementData < 0 || decrementData > MAX_DATA) {
            throw new IllegalArgumentException(EXC_DATA);
        }
        this.decrementData = decrementData;
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
        this.decrementData = in.readLong();
        this.execID = in.readInt();
        LogMgr.log(6, "001 decrementData=%d execID=%d", Long.valueOf(this.decrementData), Integer.valueOf(this.execID));
        LogMgr.log(6, "999");
    }

    private PurseDecrementData(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        LogMgr.log(6, "001 decrementData=%d execID=%d", Long.valueOf(this.decrementData), Integer.valueOf(this.execID));
        out.writeLong(this.decrementData);
        out.writeInt(this.execID);
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        long j = this.decrementData;
        if (j < 0 || j > MAX_DATA) {
            LogMgr.log(1, "%s : decrementData = %d", "800", Long.valueOf(j));
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
