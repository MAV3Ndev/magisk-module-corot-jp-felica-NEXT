package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PINEnableData extends Data {
    public static final Parcelable.Creator CREATOR = new b1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private long f83a;
    private boolean b;

    /* synthetic */ PINEnableData(Parcel parcel, b1 b1Var) {
        this(parcel);
    }

    private void d(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        boolean[] zArr = {false};
        this.f83a = parcel.readLong();
        parcel.readBooleanArray(zArr);
        this.b = zArr[0];
        com.felicanetworks.mfc.s1.a.d(6, "%s : pin = %d, enabling = %s", "999", Long.valueOf(this.f83a), Boolean.valueOf(this.b));
    }

    @Override // com.felicanetworks.mfc.Data
    public int c() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : TYPE = %d", "999", 8);
        return 8;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        boolean[] zArr = {this.b};
        parcel.writeLong(this.f83a);
        parcel.writeBooleanArray(zArr);
        com.felicanetworks.mfc.s1.a.d(4, "%s : pin = %d, enabling = %s", "999", Long.valueOf(this.f83a), Boolean.valueOf(this.b));
    }

    private PINEnableData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        d(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
