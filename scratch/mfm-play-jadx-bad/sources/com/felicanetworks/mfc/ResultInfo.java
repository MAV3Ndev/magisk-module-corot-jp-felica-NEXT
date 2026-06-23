package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class ResultInfo implements Parcelable {
    public static final Parcelable.Creator<ResultInfo> CREATOR = new Parcelable.Creator<ResultInfo>() { // from class: com.felicanetworks.mfc.ResultInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfo createFromParcel(Parcel in) {
            LogMgr.log(4, "%s", "000");
            LogMgr.log(4, "%s : in = %s", "999", in);
            return new ResultInfo(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfo[] newArray(int size) {
            LogMgr.log(4, "%s", "000");
            LogMgr.log(4, "%s : size = %d", "999", Integer.valueOf(size));
            return new ResultInfo[size];
        }
    };
    public static final int EXCEPTION_TYPE_FELICA = 1;
    public static final int EXCEPTION_TYPE_ILLEGAL_ARGUMENT = 32;
    public static final int EXCEPTION_TYPE_NONE = 0;
    public static final int EXCEPTION_TYPE_NUMBER_FORMAT = 34;
    public static final int EXCEPTION_TYPE_SECURITY = 33;
    protected int exceptionType;
    protected String message;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ResultInfo() {
        LogMgr.log(5, "%s", "000");
        this.exceptionType = 0;
        LogMgr.log(5, "%s", "999");
    }

    ResultInfo(int exceptionType, String message) {
        LogMgr.log(5, "%s : exceptionType = %d, message= % s", "000", Integer.valueOf(exceptionType), message);
        this.exceptionType = exceptionType;
        this.message = message;
        LogMgr.log(5, "%s", "999");
    }

    public int getExceptionType() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : exceptionType = %d", "999", Integer.valueOf(this.exceptionType));
        return this.exceptionType;
    }

    public String getMessage() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : message = %s", "999", this.message);
        return this.message;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        out.writeInt(this.exceptionType);
        out.writeString(this.message);
        LogMgr.log(4, "%s", "999");
    }

    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.exceptionType = in.readInt();
        this.message = in.readString();
        LogMgr.log(6, "%s : exceptionType = %d, message = %s", "999", Integer.valueOf(this.exceptionType), this.message);
    }

    protected ResultInfo(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }
}
