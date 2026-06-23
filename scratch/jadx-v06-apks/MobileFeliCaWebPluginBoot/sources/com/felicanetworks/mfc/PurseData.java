package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PurseData extends Data {
    public static final Parcelable.Creator CREATOR = new e1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private long f86a;
    private long b;
    private byte[] c;
    private int d;

    /* synthetic */ PurseData(Parcel parcel, e1 e1Var) {
        this(parcel);
    }

    private void h(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.c = new byte[6];
        this.f86a = parcel.readLong();
        this.b = parcel.readLong();
        parcel.readByteArray(this.c);
        this.d = parcel.readInt();
        com.felicanetworks.mfc.s1.a.e(6, "001 purseData=%d cashBackData=%d userData.length=%d execID=%d", Long.valueOf(this.f86a), Long.valueOf(this.b), Integer.valueOf(this.c.length), Integer.valueOf(this.d));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public int c() {
        return 3;
    }

    public long d() {
        return this.b;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int e() {
        return this.d;
    }

    public long f() {
        return this.f86a;
    }

    public byte[] g() {
        return this.c;
    }

    public void i(long j) {
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("The value must be from 0 to 0xffffffff.");
        }
        this.b = j;
    }

    public void j(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("The value must be from 0 to 0xffff.");
        }
        this.d = i;
    }

    public void k(long j) {
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("The value must be from 0 to 0xffffffff.");
        }
        this.f86a = j;
    }

    public void l(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("null is not allowed.");
        }
        if (bArr.length != 6) {
            throw new IllegalArgumentException("The length must be 6.");
        }
        this.c = bArr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.e(6, "001 purseData=%d cashBackData=%d userData.length=%d execID=%d", Long.valueOf(this.f86a), Long.valueOf(this.b), Integer.valueOf(this.c.length), Integer.valueOf(this.d));
        parcel.writeLong(this.f86a);
        parcel.writeLong(this.b);
        parcel.writeByteArray(this.c);
        parcel.writeInt(this.d);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public PurseData(long j, long j2, byte[] bArr, int i) {
        k(j);
        i(j2);
        l(bArr);
        j(i);
    }

    private PurseData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        h(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
