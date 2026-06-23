package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PINChangeData extends Data {
    public static final Parcelable.Creator CREATOR = new z0();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private long f81a;
    private long b;

    /* synthetic */ PINChangeData(Parcel parcel, z0 z0Var) {
        this(parcel);
    }

    private void d(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f81a = parcel.readLong();
        this.b = parcel.readLong();
        com.felicanetworks.mfc.s1.a.d(6, "%s : pin = %d, newPIN = %d", "999", Long.valueOf(this.f81a), Long.valueOf(this.b));
    }

    @Override // com.felicanetworks.mfc.Data
    public int c() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : TYPE = %d", "999", 7);
        return 7;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %d, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeLong(this.f81a);
        parcel.writeLong(this.b);
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
    }

    private PINChangeData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        d(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
