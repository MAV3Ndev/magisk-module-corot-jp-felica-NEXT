package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class AreaInformation implements Parcelable {
    public static final Parcelable.Creator<AreaInformation> CREATOR = new Parcelable.Creator<AreaInformation>() { // from class: com.felicanetworks.mfc.AreaInformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AreaInformation createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new AreaInformation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AreaInformation[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new AreaInformation[i];
        }
    };
    protected int areaCode;
    protected int endServiceCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AreaInformation(int i, int i2) throws IllegalArgumentException {
        LogMgr.log(4, "%s areaCode = %s : endServiceCode = %s", "000", Integer.valueOf(i), Integer.valueOf(i2));
        try {
            ServiceUtil.getInstance().checkAreaCode(i);
            this.areaCode = i;
            this.endServiceCode = i2;
            LogMgr.log(4, "%s", "999");
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", "800", e.toString());
            throw new IllegalArgumentException();
        }
    }

    public int getAreaCode() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return areaCode = %d", "999", Integer.valueOf(this.areaCode));
        return this.areaCode;
    }

    public void setAreaCode(int i) throws IllegalArgumentException {
        LogMgr.log(4, "%s areaCode = %s", "000", Integer.valueOf(i));
        try {
            ServiceUtil.getInstance().checkAreaCode(i);
            this.areaCode = i;
            LogMgr.log(4, "%s", "999");
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", "800", e.toString());
            throw new IllegalArgumentException();
        }
    }

    public int getEndServiceCode() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return endServiceCode = %d", "999", Integer.valueOf(this.endServiceCode));
        return this.endServiceCode;
    }

    public void setEndServiceCode(int i) throws IllegalArgumentException {
        LogMgr.log(4, "%s endServiceCode = %s", "001", Integer.valueOf(i));
        this.endServiceCode = i;
        LogMgr.log(4, "%s", "999");
    }

    protected void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.areaCode = parcel.readInt();
        this.endServiceCode = parcel.readInt();
        LogMgr.log(6, "001 areaCode=%d  endServiceCode=%d", Integer.valueOf(this.areaCode), Integer.valueOf(this.endServiceCode));
        LogMgr.log(6, "999");
    }

    private AreaInformation(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(6, "001 areaCode=%d  endServiceCode=%d", Integer.valueOf(this.areaCode), Integer.valueOf(this.endServiceCode));
        parcel.writeInt(this.areaCode);
        parcel.writeInt(this.endServiceCode);
        LogMgr.log(4, "999");
    }
}
