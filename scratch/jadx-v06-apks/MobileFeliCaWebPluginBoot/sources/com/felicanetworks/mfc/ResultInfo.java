package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ResultInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new p1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected int f91a;
    protected String b;

    ResultInfo() {
        com.felicanetworks.mfc.s1.a.b(5, "%s", "000");
        this.f91a = 0;
        com.felicanetworks.mfc.s1.a.b(5, "%s", "999");
    }

    public int a() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : exceptionType = %d", "999", Integer.valueOf(this.f91a));
        return this.f91a;
    }

    public String b() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : message = %s", "999", this.b);
        return this.b;
    }

    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f91a = parcel.readInt();
        this.b = parcel.readString();
        com.felicanetworks.mfc.s1.a.d(6, "%s : exceptionType = %d, message = %s", "999", Integer.valueOf(this.f91a), this.b);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeInt(this.f91a);
        parcel.writeString(this.b);
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
    }

    protected ResultInfo(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
