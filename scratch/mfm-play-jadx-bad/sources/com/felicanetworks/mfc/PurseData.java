package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PurseData extends Data {
    public static final Parcelable.Creator<PurseData> CREATOR = new Parcelable.Creator<PurseData>() { // from class: com.felicanetworks.mfc.PurseData.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseData createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new PurseData(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PurseData[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new PurseData[size];
        }
    };
    private static final String EXC_DATA = "The value must be from 0 to 0xffffffff.";
    private static final String EXC_EXEC_ID = "The value must be from 0 to 0xffff.";
    private static final String EXC_LENGTH = "The length must be 6.";
    private static final String EXC_NULL = "null is not allowed.";
    private static final long MAX_DATA = 4294967295L;
    private static final int MAX_EXEC_ID = 65535;
    private static final long MIN_DATA = 0;
    private static final int MIN_EXEC_ID = 0;
    public static final int TYPE = 3;
    private static final int USERDATA_LENGTH = 6;
    private long cashBackData;
    private int execID;
    private long purseData;
    private byte[] userData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.Data
    public int getType() {
        return 3;
    }

    public PurseData(long purseData, long cashBackData, byte[] userData, int execID) throws IllegalArgumentException {
        setPurseData(purseData);
        setCashBackData(cashBackData);
        setUserData(userData);
        setExecID(execID);
    }

    public long getPurseData() {
        return this.purseData;
    }

    public void setPurseData(long purseData) throws IllegalArgumentException {
        if (purseData < 0 || purseData > MAX_DATA) {
            throw new IllegalArgumentException(EXC_DATA);
        }
        this.purseData = purseData;
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

    public byte[] getUserData() {
        return this.userData;
    }

    public void setUserData(byte[] userData) throws IllegalArgumentException {
        if (userData == null) {
            throw new IllegalArgumentException(EXC_NULL);
        }
        if (userData.length != 6) {
            throw new IllegalArgumentException(EXC_LENGTH);
        }
        this.userData = userData;
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
        this.userData = new byte[6];
        this.purseData = in.readLong();
        this.cashBackData = in.readLong();
        in.readByteArray(this.userData);
        this.execID = in.readInt();
        LogMgr.log(6, "001 purseData=%d cashBackData=%d userData.length=%d execID=%d", Long.valueOf(this.purseData), Long.valueOf(this.cashBackData), Integer.valueOf(this.userData.length), Integer.valueOf(this.execID));
        LogMgr.log(6, "999");
    }

    private PurseData(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        LogMgr.log(6, "001 purseData=%d cashBackData=%d userData.length=%d execID=%d", Long.valueOf(this.purseData), Long.valueOf(this.cashBackData), Integer.valueOf(this.userData.length), Integer.valueOf(this.execID));
        out.writeLong(this.purseData);
        out.writeLong(this.cashBackData);
        out.writeByteArray(this.userData);
        out.writeInt(this.execID);
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        long j = this.purseData;
        if (j < 0 || j > MAX_DATA) {
            LogMgr.log(1, "%s : purseData = %d", "800", Long.valueOf(j));
            throw new IllegalArgumentException(EXC_DATA);
        }
        long j2 = this.cashBackData;
        if (j2 < 0 || j2 > MAX_DATA) {
            LogMgr.log(1, "%s : cashBackData = %d", "801", Long.valueOf(j2));
            throw new IllegalArgumentException(EXC_DATA);
        }
        byte[] bArr = this.userData;
        if (bArr == null) {
            LogMgr.log(1, "%s", "802");
            throw new IllegalArgumentException(EXC_NULL);
        }
        if (bArr.length != 6) {
            LogMgr.log(1, "%s", "803");
            throw new IllegalArgumentException(EXC_LENGTH);
        }
        int i = this.execID;
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s : execID = %d", "804", Integer.valueOf(i));
            throw new IllegalArgumentException(EXC_EXEC_ID);
        }
        LogMgr.log(4, "999");
    }
}
