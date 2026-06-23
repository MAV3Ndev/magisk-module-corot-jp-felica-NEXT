package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class PINCheckData extends Data {
    public static final Parcelable.Creator<PINCheckData> CREATOR = new Parcelable.Creator<PINCheckData>() { // from class: com.felicanetworks.mfc.PINCheckData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PINCheckData createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new PINCheckData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PINCheckData[] newArray(int i) {
            LogMgr.log(4, "%s : int size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new PINCheckData[i];
        }
    };
    private static final String EXC_PIN = "The value must be from 0 to 0xffffffff.";
    private static final long MAX_PIN = 4294967295L;
    private static final long MIN_PIN = 0;
    public static final int TYPE = 6;
    private long pin;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PINCheckData(long j) throws IllegalArgumentException {
        LogMgr.log(4, "%s : pin = %d", "000", Long.valueOf(j));
        LogMgr.log(4, "%s", "999");
        setPIN(j);
    }

    @Override // com.felicanetworks.mfc.Data
    public int getType() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : TYPE = %d", (Object) "999", (Object) 6);
        return 6;
    }

    public long getPIN() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : pin = %d", "999", Long.valueOf(this.pin));
        return this.pin;
    }

    public void setPIN(long j) throws IllegalArgumentException {
        LogMgr.log(4, "%s : pin = %d", "000", Long.valueOf(j));
        if (j < 0 || j > MAX_PIN) {
            LogMgr.log(1, "%s : Throw IllegalArgumentException EXC_PIN", "800");
            throw new IllegalArgumentException(EXC_PIN);
        }
        this.pin = j;
        LogMgr.log(4, "%s : this.pin = %d", "999", Long.valueOf(j));
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        long j = parcel.readLong();
        this.pin = j;
        LogMgr.log(6, "%s : pin = %d", "999", Long.valueOf(j));
    }

    private PINCheckData(Parcel parcel) {
        LogMgr.log(6, "%s : in = %d", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "%s", "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeLong(this.pin);
        LogMgr.log(4, "%s : pin = %d", "999", Long.valueOf(this.pin));
    }

    @Override // com.felicanetworks.mfc.Data
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        long j = this.pin;
        if (j < 0 || j > MAX_PIN) {
            LogMgr.log(6, "%s : Throw IllegalArgumentException EXC_PIN", "800");
            throw new IllegalArgumentException(EXC_PIN);
        }
        LogMgr.log(4, "%s", "999");
    }
}
