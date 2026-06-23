package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PINCheckData extends Data {
    public static final Parcelable.Creator CREATOR = new a1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private long f82a;

    /* synthetic */ PINCheckData(Parcel parcel, a1 a1Var) {
        this(parcel);
    }

    private void d(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        long j = parcel.readLong();
        this.f82a = j;
        com.felicanetworks.mfc.s1.a.c(6, "%s : pin = %d", "999", Long.valueOf(j));
    }

    @Override // com.felicanetworks.mfc.Data
    public int c() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s : TYPE = %d", "999", 6);
        return 6;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeLong(this.f82a);
        com.felicanetworks.mfc.s1.a.c(4, "%s : pin = %d", "999", Long.valueOf(this.f82a));
    }

    private PINCheckData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %d", "000", parcel);
        d(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
