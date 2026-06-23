package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoType<E> extends FelicaResultInfo implements Parcelable {
    protected E value;

    public FelicaResultInfoType(E value) {
        LogMgr.log(4, "%s value=%s", "000", value);
        this.value = value;
        LogMgr.log(6, "999");
    }

    public FelicaResultInfoType(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s exceptionType=%d message=%s", "000", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoType(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoType(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d statusFlag1=%d statusFlag2=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        LogMgr.log(4, "%s", "999");
    }

    public E getValue() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s value = %s", "999", this.value);
        return this.value;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(7, "%s", "000");
        super.writeToParcel(out, flag);
        LogMgr.log(7, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        LogMgr.log(7, "%s", "000");
        super.readFromParcel(in);
        LogMgr.log(7, "%s", "999");
    }
}
