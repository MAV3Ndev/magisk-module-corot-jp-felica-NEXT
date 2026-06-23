package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoType extends FelicaResultInfo implements Parcelable {
    protected Object h;

    public FelicaResultInfoType(Object obj) {
        com.felicanetworks.mfc.s1.a.c(4, "%s value=%s", "000", obj);
        this.h = obj;
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        super.c(parcel);
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public Object i() {
        com.felicanetworks.mfc.s1.a.b(4, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(4, "%s value = %s", "999", this.h);
        return this.h;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        super.writeToParcel(parcel, i);
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }
}
