package com.felicanetworks.semc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.semc.util.LogMgrUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ResultInfo implements Parcelable {
    public static final Parcelable.Creator<ResultInfo> CREATOR = new Parcelable.Creator<ResultInfo>() { // from class: com.felicanetworks.semc.ResultInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfo createFromParcel(Parcel parcel) {
            LogMgrUtil.log(6, "000");
            LogMgrUtil.log(6, "999 : in = " + parcel);
            return new ResultInfo(parcel);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfo[] newArray(int i) {
            LogMgrUtil.log(6, "000");
            LogMgrUtil.log(6, "999 : size = " + i);
            return new ResultInfo[i];
        }
    };
    public static final int EXCEPTION_TYPE_ILLEGAL_ARGUMENT = 32;
    public static final int EXCEPTION_TYPE_ILLEGAL_STATE = 33;
    public static final int EXCEPTION_TYPE_NONE = 0;
    public static final int EXCEPTION_TYPE_NONE_NEEDS_RECONNECT = 2;
    public static final int EXCEPTION_TYPE_SEM_CLIENT = 1;
    protected int mExceptionType;
    protected String mMessage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ResultInfo() {
        LogMgrUtil.log(7, "000");
        this.mExceptionType = 0;
        LogMgrUtil.log(7, "999");
    }

    ResultInfo(int i, String str) {
        LogMgrUtil.log(7, "000 : exceptionType = " + i + " message= " + str);
        this.mExceptionType = i;
        this.mMessage = str;
        LogMgrUtil.log(7, "999");
    }

    public int getExceptionType() {
        LogMgrUtil.log(8, "000");
        LogMgrUtil.log(8, "999 : exceptionType = " + this.mExceptionType);
        return this.mExceptionType;
    }

    public String getMessage() {
        LogMgrUtil.log(8, "000");
        LogMgrUtil.log(8, "999 : message = " + this.mMessage);
        return this.mMessage;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgrUtil.log(6, "000 : out = " + parcel + " flag = " + i);
        parcel.writeInt(this.mExceptionType);
        parcel.writeString(this.mMessage);
        LogMgrUtil.log(6, "999");
    }

    protected void readFromParcel(Parcel parcel) {
        LogMgrUtil.log(8, "000 : in = " + parcel);
        this.mExceptionType = parcel.readInt();
        this.mMessage = parcel.readString();
        LogMgrUtil.log(8, "999 : exceptionType = " + this.mExceptionType + " message = " + this.mMessage);
    }

    protected ResultInfo(Parcel parcel) {
        LogMgrUtil.log(8, "000 : in = " + parcel);
        readFromParcel(parcel);
        LogMgrUtil.log(8, "999");
    }
}
