package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfo extends ResultInfo {
    public static final Parcelable.Creator<FelicaResultInfo> CREATOR = new Parcelable.Creator<FelicaResultInfo>() { // from class: com.felicanetworks.mfc.FelicaResultInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfo createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfo[] newArray(int size) {
            LogMgr.log(4, "%s : in = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo[size];
        }
    };
    protected int id;
    protected AppInfo otherAppInfo;
    protected int statusFlag1;
    protected int statusFlag2;
    protected int type;

    public FelicaResultInfo() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfo(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s", "000", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfo(int exceptionType, String message, int id, int type) {
        super(exceptionType, message);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        this.id = id;
        this.type = type;
        LogMgr.log(4, "%s : id = %d, type = %d", "999", Integer.valueOf(id), Integer.valueOf(this.type));
    }

    public FelicaResultInfo(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        this(exceptionType, message, id, type);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, statusFlag1 = %d, statusFlag = %d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        this.statusFlag1 = statusFlag1;
        this.statusFlag2 = statusFlag2;
        LogMgr.log(4, "%s : statusFlag1 = %d, statusFlag2 = %d", "999", Integer.valueOf(statusFlag1), Integer.valueOf(this.statusFlag2));
    }

    public FelicaResultInfo(int exceptionType, String message, int id, int type, AppInfo otherAppInfo) {
        this(exceptionType, message, id, type);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, otherAppInfo = %d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), otherAppInfo);
        this.otherAppInfo = otherAppInfo;
        LogMgr.log(4, "%s : otherAppPID = %d", "999", otherAppInfo);
    }

    public int getId() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : id = %d", "999", Integer.valueOf(this.id));
        return this.id;
    }

    public int getType() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : type = %d", "999", Integer.valueOf(this.type));
        return this.type;
    }

    public int getStatusFlag1() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : statusFlag1 = %d", "999", Integer.valueOf(this.statusFlag1));
        return this.statusFlag1;
    }

    public int getStatusFlag2() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : statusFlag2 = %d", "999", Integer.valueOf(this.statusFlag2));
        return this.statusFlag2;
    }

    public AppInfo getOtherAppInfo() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s : otherAppPID = %d", "999", this.otherAppInfo);
        return this.otherAppInfo;
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        super.writeToParcel(out, flag);
        LogMgr.log(4, "%s : in = %s, flag = %d", "000", out, Integer.valueOf(flag));
        out.writeInt(getId());
        out.writeInt(getType());
        out.writeInt(getStatusFlag1());
        out.writeInt(getStatusFlag2());
        out.writeParcelable(this.otherAppInfo, flag);
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.id = in.readInt();
        this.type = in.readInt();
        this.statusFlag1 = in.readInt();
        this.statusFlag2 = in.readInt();
        if (Build.VERSION.SDK_INT >= 33) {
            this.otherAppInfo = (AppInfo) in.readParcelable(AppInfo.class.getClassLoader(), AppInfo.class);
        } else {
            this.otherAppInfo = (AppInfo) in.readParcelable(AppInfo.class.getClassLoader());
        }
        LogMgr.log(6, "%s", "999");
    }

    private FelicaResultInfo(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }
}
