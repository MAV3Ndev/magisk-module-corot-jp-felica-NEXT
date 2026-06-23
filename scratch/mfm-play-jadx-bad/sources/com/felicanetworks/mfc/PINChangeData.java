package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PINChangeData extends Data {
    public static final Parcelable.Creator<PINChangeData> CREATOR = new Parcelable.Creator<PINChangeData>() { // from class: com.felicanetworks.mfc.PINChangeData.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PINChangeData createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new PINChangeData(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PINChangeData[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new PINChangeData[size];
        }
    };
    private static final String EXC_PIN = "The value must be from 0 to 0xffffffff.";
    private static final long MAX_PIN = 4294967295L;
    private static final long MIN_PIN = 0;
    public static final int TYPE = 7;
    private long newPIN;
    private long pin;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PINChangeData(long pin, long newPIN) throws IllegalArgumentException {
        LogMgr.log(4, "%s : pin = %d, newPIN = %d", "000", Long.valueOf(pin), Long.valueOf(newPIN));
        setPIN(pin);
        setNewPIN(newPIN);
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public int getType() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : TYPE = %d", (Object) "999", (Object) 7);
        return 7;
    }

    public long getPIN() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : pin = %d", "999", Long.valueOf(this.pin));
        return this.pin;
    }

    public void setPIN(long pin) throws IllegalArgumentException {
        LogMgr.log(4, "%s : pin = %d", "000", Long.valueOf(pin));
        if (pin < 0 || pin > MAX_PIN) {
            LogMgr.log(1, "%s : pin = %d, Throw IllegalArgumentException EXC_PIN", "800", Long.valueOf(pin));
            throw new IllegalArgumentException(EXC_PIN);
        }
        this.pin = pin;
        LogMgr.log(4, "%s : this.pin = %d", "999", Long.valueOf(pin));
    }

    public long getNewPIN() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : newPIN = %d", "999", Long.valueOf(this.newPIN));
        return this.newPIN;
    }

    public void setNewPIN(long newPIN) throws IllegalArgumentException {
        LogMgr.log(4, "%s : newPIN = %d", "000", Long.valueOf(newPIN));
        if (newPIN < 0 || newPIN > MAX_PIN) {
            LogMgr.log(1, "%s : pin = %d, Throw IllegalArgumentException EXC_PIN", "800", Long.valueOf(newPIN));
            throw new IllegalArgumentException(EXC_PIN);
        }
        this.newPIN = newPIN;
        LogMgr.log(4, "%s : this.newPIN = %d", "999", Long.valueOf(newPIN));
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.pin = in.readLong();
        this.newPIN = in.readLong();
        LogMgr.log(6, "%s : pin = %d, newPIN = %d", "999", Long.valueOf(this.pin), Long.valueOf(this.newPIN));
    }

    private PINChangeData(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %d, flag = %d", "000", out, Integer.valueOf(flag));
        out.writeLong(this.pin);
        out.writeLong(this.newPIN);
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        long j = this.pin;
        if (j < 0 || j > MAX_PIN) {
            LogMgr.log(6, "%s : pin = %d, Throw IllegalArgumentException EXC_PIN", "800", Long.valueOf(j));
            throw new IllegalArgumentException(EXC_PIN);
        }
        long j2 = this.newPIN;
        if (j2 < 0 || j2 > MAX_PIN) {
            LogMgr.log(6, "%s : pin = %d, Throw IllegalArgumentException EXC_PIN", "801", Long.valueOf(j2));
            throw new IllegalArgumentException(EXC_PIN);
        }
        LogMgr.log(4, "%s", "999");
    }
}
