package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfo extends ResultInfo {
    public static final Parcelable.Creator<FelicaResultInfo> CREATOR = new Parcelable.Creator<FelicaResultInfo>() { // from class: com.felicanetworks.mfc.FelicaResultInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfo createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfo[] newArray(int i) {
            LogMgr.log(4, "%s : in = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo[i];
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

    public FelicaResultInfo(int i, String str) {
        super(i, str);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s", "000", Integer.valueOf(i), str);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfo(int i, String str, int i2, int i3) {
        super(i, str);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3));
        this.id = i2;
        this.type = i3;
        LogMgr.log(4, "%s : id = %d, type = %d", "999", Integer.valueOf(i2), Integer.valueOf(this.type));
    }

    public FelicaResultInfo(int i, String str, int i2, int i3, int i4, int i5) {
        this(i, str, i2, i3);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, statusFlag1 = %d, statusFlag = %d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        this.statusFlag1 = i4;
        this.statusFlag2 = i5;
        LogMgr.log(4, "%s : statusFlag1 = %d, statusFlag2 = %d", "999", Integer.valueOf(i4), Integer.valueOf(this.statusFlag2));
    }

    public FelicaResultInfo(int i, String str, int i2, int i3, AppInfo appInfo) {
        this(i, str, i2, i3);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, otherAppInfo = %d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3), appInfo);
        this.otherAppInfo = appInfo;
        LogMgr.log(4, "%s : otherAppPID = %d", "999", appInfo);
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
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        LogMgr.log(4, "%s : in = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeInt(getId());
        parcel.writeInt(getType());
        parcel.writeInt(getStatusFlag1());
        parcel.writeInt(getStatusFlag2());
        parcel.writeParcelable(this.otherAppInfo, i);
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.id = parcel.readInt();
        this.type = parcel.readInt();
        this.statusFlag1 = parcel.readInt();
        this.statusFlag2 = parcel.readInt();
        this.otherAppInfo = (AppInfo) parcel.readParcelable(AppInfo.class.getClassLoader());
        LogMgr.log(6, "%s", "999");
    }

    private FelicaResultInfo(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "%s", "999");
    }
}
