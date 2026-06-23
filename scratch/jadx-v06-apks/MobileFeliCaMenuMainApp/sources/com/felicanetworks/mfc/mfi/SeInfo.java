package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class SeInfo implements Parcelable {
    public static final Parcelable.Creator<SeInfo> CREATOR = new Parcelable.Creator<SeInfo>() { // from class: com.felicanetworks.mfc.mfi.SeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SeInfo createFromParcel(Parcel parcel) {
            return new SeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SeInfo[] newArray(int i) {
            return new SeInfo[i];
        }
    };
    public static final String SE_TYPE_00 = "00";
    public static final String SE_TYPE_10 = "10";
    private String mPlatformType;
    private String mSeId;
    private String mSeType;
    private String mSepId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SeInfo(String str, String str2, String str3, String str4) {
        this.mSeId = str;
        this.mSepId = str2;
        this.mPlatformType = str3;
        this.mSeType = str4;
    }

    protected SeInfo(Parcel parcel) {
        this.mSeId = parcel.readString();
        this.mSepId = parcel.readString();
        this.mPlatformType = parcel.readString();
        this.mSeType = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(7, "001 seId=%d sepId=%d", this.mSeId, this.mSepId);
        parcel.writeString(this.mSeId);
        parcel.writeString(this.mSepId);
        parcel.writeString(this.mPlatformType);
        parcel.writeString(this.mSeType);
        LogMgr.log(7, "999");
    }

    public String getSeId() {
        return this.mSeId;
    }

    public String getSepId() {
        return this.mSepId;
    }

    public String getPlatformType() {
        return this.mPlatformType;
    }

    public String getSeType() {
        return this.mSeType;
    }
}
