package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoType<E> extends FelicaResultInfo implements Parcelable {
    protected E value;

    public FelicaResultInfoType(E e) {
        LogMgr.log(4, "%s value=%s", "000", e);
        this.value = e;
        LogMgr.log(6, "999");
    }

    public FelicaResultInfoType(int i, String str) {
        super(i, str);
        LogMgr.log(4, "%s exceptionType=%d message=%s", "000", Integer.valueOf(i), str);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoType(int i, String str, int i2, int i3) {
        super(i, str, i2, i3);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoType(int i, String str, int i2, int i3, int i4, int i5) {
        super(i, str, i2, i3, i4, i5);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d statusFlag1=%d statusFlag2=%d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        LogMgr.log(4, "%s", "999");
    }

    public E getValue() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s value = %s", "999", this.value);
        return this.value;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(7, "%s", "000");
        super.writeToParcel(parcel, i);
        LogMgr.log(7, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel parcel) {
        LogMgr.log(7, "%s", "000");
        super.readFromParcel(parcel);
        LogMgr.log(7, "%s", "999");
    }
}
