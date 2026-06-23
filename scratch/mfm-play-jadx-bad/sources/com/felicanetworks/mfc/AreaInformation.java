package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class AreaInformation implements Parcelable {
    public static final Parcelable.Creator<AreaInformation> CREATOR = new Parcelable.Creator<AreaInformation>() { // from class: com.felicanetworks.mfc.AreaInformation.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AreaInformation createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new AreaInformation(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AreaInformation[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new AreaInformation[size];
        }
    };
    protected int areaCode;
    protected int endServiceCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AreaInformation(int areaCode, int endServiceCode) throws IllegalArgumentException {
        LogMgr.log(4, "%s areaCode = %s : endServiceCode = %s", "000", Integer.valueOf(areaCode), Integer.valueOf(endServiceCode));
        try {
            ServiceUtil.getInstance().checkAreaCode(areaCode);
            this.areaCode = areaCode;
            this.endServiceCode = endServiceCode;
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

    public void setAreaCode(int areaCode) throws IllegalArgumentException {
        LogMgr.log(4, "%s areaCode = %s", "000", Integer.valueOf(areaCode));
        try {
            ServiceUtil.getInstance().checkAreaCode(areaCode);
            this.areaCode = areaCode;
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

    public void setEndServiceCode(int endServiceCode) throws IllegalArgumentException {
        LogMgr.log(4, "%s endServiceCode = %s", "001", Integer.valueOf(endServiceCode));
        this.endServiceCode = endServiceCode;
        LogMgr.log(4, "%s", "999");
    }

    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.areaCode = in.readInt();
        this.endServiceCode = in.readInt();
        LogMgr.log(6, "001 areaCode=%d  endServiceCode=%d", Integer.valueOf(this.areaCode), Integer.valueOf(this.endServiceCode));
        LogMgr.log(6, "999");
    }

    private AreaInformation(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        LogMgr.log(6, "001 areaCode=%d  endServiceCode=%d", Integer.valueOf(this.areaCode), Integer.valueOf(this.endServiceCode));
        out.writeInt(this.areaCode);
        out.writeInt(this.endServiceCode);
        LogMgr.log(4, "999");
    }
}
