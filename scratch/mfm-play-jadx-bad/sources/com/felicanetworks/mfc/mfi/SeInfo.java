package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class SeInfo implements Parcelable {
    public static final Parcelable.Creator<SeInfo> CREATOR = new Parcelable.Creator<SeInfo>() { // from class: com.felicanetworks.mfc.mfi.SeInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SeInfo createFromParcel(Parcel in) {
            return new SeInfo(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SeInfo[] newArray(int size) {
            return new SeInfo[size];
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

    public SeInfo(String seId, String sepId, String platformType, String seType) {
        this.mSeId = seId;
        this.mSepId = sepId;
        this.mPlatformType = platformType;
        this.mSeType = seType;
    }

    protected SeInfo(Parcel in) {
        this.mSeId = in.readString();
        this.mSepId = in.readString();
        this.mPlatformType = in.readString();
        this.mSeType = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        LogMgr.log(7, "001 seId=%d sepId=%d", this.mSeId, this.mSepId);
        out.writeString(this.mSeId);
        out.writeString(this.mSepId);
        out.writeString(this.mPlatformType);
        out.writeString(this.mSeType);
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
