package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PurseCashBackData extends Data {
    public static final Parcelable.Creator CREATOR = new d1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private long f85a;
    private int b;

    /* synthetic */ PurseCashBackData(Parcel parcel, d1 d1Var) {
        this(parcel);
    }

    private void d(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f85a = parcel.readLong();
        this.b = parcel.readInt();
        com.felicanetworks.mfc.s1.a.c(6, "001 cashBackData=%d execID=%d", Long.valueOf(this.f85a), Integer.valueOf(this.b));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public int c() {
        return 4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void e(long j) {
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("The value must be from 0 to 0xffffffff.");
        }
        this.f85a = j;
    }

    public void f(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("The value must be from 0 to 0xffff.");
        }
        this.b = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeLong(this.f85a);
        parcel.writeInt(this.b);
        com.felicanetworks.mfc.s1.a.c(6, "001 cashBackData=%d execID=%d", Long.valueOf(this.f85a), Integer.valueOf(this.b));
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public PurseCashBackData(long j, int i) {
        e(j);
        f(i);
    }

    private PurseCashBackData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        d(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
