package com.felicanetworks.semc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.semc.util.LogMgrUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientResultInfo extends ResultInfo {
    public static final Parcelable.Creator<SemClientResultInfo> CREATOR = new Parcelable.Creator<SemClientResultInfo>() { // from class: com.felicanetworks.semc.SemClientResultInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClientResultInfo createFromParcel(Parcel parcel) {
            LogMgrUtil.log(6, "000 : in = " + parcel);
            LogMgrUtil.log(6, "999");
            return new SemClientResultInfo(parcel);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClientResultInfo[] newArray(int i) {
            LogMgrUtil.log(6, "000 : in = " + i);
            LogMgrUtil.log(6, "999");
            return new SemClientResultInfo[i];
        }
    };
    protected int mErrorCode;
    private int mIntReturnValue;
    private String mStringReturnValue;

    @Override // com.felicanetworks.semc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemClientResultInfo() {
        LogMgrUtil.log(6, "000");
        LogMgrUtil.log(6, "999");
    }

    public SemClientResultInfo(int i) {
        LogMgrUtil.log(6, "000 value=" + i);
        this.mIntReturnValue = i;
        LogMgrUtil.log(6, "999");
    }

    public SemClientResultInfo(String str) {
        LogMgrUtil.log(6, "000 value=" + str);
        this.mStringReturnValue = str;
        LogMgrUtil.log(6, "999");
    }

    public SemClientResultInfo(int i, String str) {
        super(i, str);
        LogMgrUtil.log(6, "000 : exceptionType = " + i + ", message = " + str);
        LogMgrUtil.log(6, "999");
    }

    public SemClientResultInfo(int i, String str, int i2) {
        super(i, str);
        LogMgrUtil.log(6, "000 : exceptionType = " + i + " message = " + str + " errorCode = " + i2);
        this.mErrorCode = i2;
        StringBuilder sb = new StringBuilder("999 : errorCode = ");
        sb.append(this.mErrorCode);
        LogMgrUtil.log(6, sb.toString());
    }

    public int getErrorCode() {
        LogMgrUtil.log(8, "000");
        LogMgrUtil.log(8, "999 : errorCode = " + this.mErrorCode);
        return this.mErrorCode;
    }

    public int getIntReturnValue() {
        LogMgrUtil.log(8, "000");
        LogMgrUtil.log(8, "999 : value = " + this.mIntReturnValue);
        return this.mIntReturnValue;
    }

    public String getStringReturnValue() {
        LogMgrUtil.log(8, "000");
        LogMgrUtil.log(8, "999 : value = " + this.mStringReturnValue);
        return this.mStringReturnValue;
    }

    @Override // com.felicanetworks.semc.ResultInfo
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        LogMgrUtil.log(8, "000 : in = " + parcel);
        this.mErrorCode = parcel.readInt();
        this.mIntReturnValue = parcel.readInt();
        this.mStringReturnValue = parcel.readString();
        LogMgrUtil.log(8, "999");
    }

    private SemClientResultInfo(Parcel parcel) {
        LogMgrUtil.log(8, "000 : in = " + parcel);
        readFromParcel(parcel);
        LogMgrUtil.log(8, "999");
    }

    @Override // com.felicanetworks.semc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        LogMgrUtil.log(6, "000 : out = " + parcel + "flag = " + i);
        parcel.writeInt(getErrorCode());
        parcel.writeInt(getIntReturnValue());
        parcel.writeString(getStringReturnValue());
        LogMgrUtil.log(6, "999");
    }
}
