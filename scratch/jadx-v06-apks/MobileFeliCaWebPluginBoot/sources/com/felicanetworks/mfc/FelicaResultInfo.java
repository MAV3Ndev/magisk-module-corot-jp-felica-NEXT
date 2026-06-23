package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfo extends ResultInfo {
    public static final Parcelable.Creator CREATOR = new y();
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected AppInfo g;

    /* synthetic */ FelicaResultInfo(Parcel parcel, y yVar) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        super.c(parcel);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.c = parcel.readInt();
        this.d = parcel.readInt();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.g = (AppInfo) parcel.readParcelable(AppInfo.class.getClassLoader());
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public int d() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : id = %d", "999", Integer.valueOf(this.c));
        return this.c;
    }

    public AppInfo e() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : otherAppPID = %d", "999", this.g);
        return this.g;
    }

    public int f() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : statusFlag1 = %d", "999", Integer.valueOf(this.e));
        return this.e;
    }

    public int g() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : statusFlag2 = %d", "999", Integer.valueOf(this.f));
        return this.f;
    }

    public int h() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : type = %d", "999", Integer.valueOf(this.d));
        return this.d;
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        com.felicanetworks.mfc.s1.a.d(4, "%s : in = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeInt(d());
        parcel.writeInt(h());
        parcel.writeInt(f());
        parcel.writeInt(g());
        parcel.writeParcelable(this.g, i);
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
    }

    public FelicaResultInfo() {
        com.felicanetworks.mfc.s1.a.b(4, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
    }

    private FelicaResultInfo(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
